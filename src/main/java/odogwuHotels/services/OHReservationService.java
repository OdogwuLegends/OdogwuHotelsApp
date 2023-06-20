package odogwuHotels.services;

import odogwuHotels.myUtils.Map;
import odogwuHotels.myUtils.Utils;
import odogwuHotels.data.models.Customer;
import odogwuHotels.data.models.Receipt;
import odogwuHotels.data.models.Reservation;
import odogwuHotels.data.models.Room;
import odogwuHotels.data.repositories.OHReservationRepository;
import odogwuHotels.data.repositories.ReservationRepository;
import odogwuHotels.dto.requests.ReceiptRequest;
import odogwuHotels.dto.requests.ReservationRequest;
import odogwuHotels.dto.requests.UpdateReservationRequest;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.EntityNotFoundException;

import java.util.List;

public class OHReservationService implements ReservationService{
    private final ReservationRepository reservationRepository = OHReservationRepository.createObject();
    CustomerService customerService;
    RoomService roomService;
    ReceiptService receiptService;


    @Override
    public ReservationResponse makeReservation(ReservationRequest request) {
        customerService = new OHCustomerService();
        roomService = new OHRoomService();

        SearchResponse room = new SearchResponse();
        try {
            room = roomService.findRoomByIdOrRoomNumber(Map.reservationReqToRoomSearchResponse(request));
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }

        Room roomToReserve = Map.roomSearchResponseToRoom(room);
        UserResponse userResponse = new UserResponse();
        try {
            userResponse = customerService.findCustomerByEmail(request.getEmail());
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        Customer foundCustomer = Map.customerResponseToCustomer(userResponse);
        Reservation newReservation = reservationRepository.saveReservation(Map.reservationReqToReservation(request,foundCustomer,roomToReserve));
        ReservationResponse response = Map.reservationToReservationResponse(newReservation);
        response.setMessage("Reservation successful.");
        return response;
    }
    @Override
    public ReservationResponse findReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException {
        Reservation foundReservation = reservationRepository.findReservationByRoomNumber(request.getRoomNumberChosen());
        if(foundReservation == null){
            throw new EntityNotFoundException("Reservation not found");
        }
        return Map.reservationToReservationResponse(foundReservation);
    }
    @Override
    public ReservationResponse findReservationById(int id) throws EntityNotFoundException {
        Reservation foundReservation = reservationRepository.findReservationById(id);
        if(foundReservation == null){
            throw new EntityNotFoundException("Reservation not found");
        }
        return Map.reservationToReservationResponse(foundReservation);
    }

    @Override
    public UpdateResponse updateReservation(UpdateReservationRequest request) {
        roomService = new OHRoomService();

        Reservation reservationToUpdate = reservationRepository.findReservationByRoomNumber(request.getRoomNumberChosen());
        int index = reservationRepository.getIndex(reservationToUpdate);

        UpdateResponse response = new UpdateResponse();

        if(reservationToUpdate != null){
            if(request.getFirstName() != null) reservationToUpdate.getCustomer().setFirstName(request.getFirstName());
            if(request.getLastName() != null) reservationToUpdate.getCustomer().setLastName(request.getLastName());
            if(request.getEmail() != null) reservationToUpdate.getCustomer().setEmail(request.getEmail());

            if(request.getNewRoomNumberChosen() != 0) {
                SearchResponse room = new SearchResponse();
                try {
                    room = roomService.findRoomByIdOrRoomNumber(Map.updateReservationRequestToRoomSearchResponse(request));
                } catch (EntityNotFoundException ex){
                    System.err.println(ex.getMessage());
                }

                Room newRoom = Map.roomSearchResponseToRoom(room);
                reservationToUpdate.getRoom().setRoomNumber(newRoom.getRoomNumber());
                reservationToUpdate.getRoom().setRoomType(newRoom.getRoomType());
                reservationToUpdate.getRoom().setPrice(newRoom.getPrice());
                if(request.isAvailable() != reservationToUpdate.getRoom().isAvailable()) reservationToUpdate.getRoom().setAvailable(request.isAvailable());
            }
            if(request.getCheckInDate() != null) reservationToUpdate.setCheckInDate(Utils.stringToLocalDate(request.getCheckInDate()));
            if(request.getCheckOutDate() != null) reservationToUpdate.setCheckOutDate(Utils.stringToLocalDate(request.getCheckOutDate()));

            Reservation updatedReservation = reservationRepository.updateReservation(index,reservationToUpdate);

            response = Map.updateReservationToResponse(updatedReservation);
            response.setMessage("Update Successful");
        }
        else {
            response.setMessage("Reservation not found");
        }
        return response;
    }
    @Override
    public ReservationResponse checkIn(ReceiptRequest request) throws EntityNotFoundException {
        receiptService = new OHReceiptService();

        ReceiptResponse receipt = receiptService.findReceiptByEmail(request.getEmail());
        if(receipt == null){
            throw new EntityNotFoundException("Receipt not found");
        }
        Receipt foundReceipt = Map.responseToReceipt(receipt);

        ReservationResponse response = new ReservationResponse();
        if(foundReceipt.isFullyPaidFor()){
            Reservation foundReservation = reservationRepository.findReservationByRoomNumber(request.getRoomNumber());
            if(foundReservation.getRoom().isAvailable()) foundReservation.getRoom().setAvailable(false);
            response = Map.reservationToReservationResponse(foundReservation);
            response.setMessage("Check In Successful");
        }
        else {
            response.setMessage("Check In Not Successful. Payment Not Complete");
        }
        return response;
    }
    @Override
    public ReservationResponse checkOut(ReceiptRequest request){
        receiptService = new OHReceiptService();
        Reservation foundReservation = reservationRepository.findReservationByRoomNumber(request.getRoomNumber());
        if(!foundReservation.getRoom().isAvailable()) foundReservation.getRoom().setAvailable(true);
        ReservationResponse response = Map.reservationToReservationResponse(foundReservation);
        response.setMessage("Check Out Successful");
        return response;
    }
    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllReservations();
    }

    @Override
    public DeleteResponse deleteReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException {
        Reservation foundReservation = reservationRepository.findReservationByRoomNumber(request.getRoomNumberChosen());
        if(foundReservation == null){
            throw new EntityNotFoundException("Reservation not found");
        }
        reservationRepository.deleteReservationByRoomNumber(foundReservation.getRoom().getRoomNumber());
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Reservation deleted successfully.");
        return response;
    }

    @Override
    public DeleteResponse deleteReservationById(int id) throws EntityNotFoundException {
        Reservation foundReservation = reservationRepository.findReservationById(id);
        if(foundReservation == null){
            throw new EntityNotFoundException("Reservation not found");
        }
        reservationRepository.deleteReservationByRoomNumber(foundReservation.getId());
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Reservation deleted successfully.");
        return response;
    }
}
