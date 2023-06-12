package odogwuHotels.services;

import odogwuHotels.Map;
import odogwuHotels.Utils;
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

import java.util.List;

public class OHReservationService implements ReservationService{
    private final ReservationRepository reservationRepository = new OHReservationRepository();
    CustomerService customerService;
    RoomService roomService;
    ReceiptService receiptService;


    @Override
    public ReservationResponse makeReservation(ReservationRequest request) {
        customerService = new OHCustomerService();
        roomService = new OHRoomService();

        SearchResponse room = roomService.findRoomByIdOrRoomNumber(Map.reservationReqToRoomSearchResponse(request));
        Room roomToReserve = Map.roomSearchResponseToRoom(room);
        UserResponse userResponse = new UserResponse();
        try {
            userResponse = customerService.findCustomerByEmail(request.getEmail());
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        Customer foundCustomer = Map.customerResponseToCustomer(userResponse);
        Reservation newReservation = reservationRepository.saveReservation(Map.reservationReqToReservation(request,foundCustomer,roomToReserve));
        ReservationResponse response = Map.reservationToReservationResponse(newReservation);
        response.setMessage("Reservation successful.");
        return response;
    }
    @Override
    public ReservationResponse findReservationByRoomNumber(ReservationRequest request) {
        Reservation foundReservation = reservationRepository.findReservationByRoomNumber(request.getRoomNumberChosen());
        return Map.reservationToReservationResponse(foundReservation);
    }
    @Override
    public ReservationResponse findReservationById(int id) {
        Reservation foundReservation = reservationRepository.findReservationById(id);
        return Map.reservationToReservationResponse(foundReservation);
    }

    @Override
    public UpdateResponse updateReservation(UpdateReservationRequest request) {
        roomService = new OHRoomService();

        Reservation reservationToUpdate = reservationRepository.findReservationByRoomNumber(request.getRoomNumberChosen());
        int index = reservationRepository.getIndex(reservationToUpdate);

        if(reservationToUpdate != null){
            if(request.getFirstName() != null) reservationToUpdate.getCustomer().setFirstName(request.getFirstName());
            if(request.getLastName() != null) reservationToUpdate.getCustomer().setLastName(request.getLastName());
            if(request.getEmail() != null) reservationToUpdate.getCustomer().setEmail(request.getEmail());
            if(request.getNewRoomNumberChosen() > 0) {
                SearchResponse room = roomService.findRoomByIdOrRoomNumber(Map.reservationReqToRoomSearchResponse(request));
                Room newRoom = Map.roomSearchResponseToRoom(room);
                reservationToUpdate.getRoom().setRoomNumber(newRoom.getRoomNumber());
                reservationToUpdate.getRoom().setRoomType(newRoom.getRoomType());
                reservationToUpdate.getRoom().setPrice(newRoom.getPrice());
                if(request.isAvailable() != reservationToUpdate.getRoom().isAvailable()) reservationToUpdate.getRoom().setAvailable(request.isAvailable());
            }
            if(request.getCheckInDate() != null) reservationToUpdate.setCheckInDate(Utils.stringToLocalDate(request.getCheckInDate()));
            if(request.getCheckOutDate() != null) reservationToUpdate.setCheckOutDate(Utils.stringToLocalDate(request.getCheckOutDate()));
        }
        reservationRepository.updateReservation(index,reservationToUpdate);
        assert reservationToUpdate != null;
        UpdateResponse response = Map.updateReservationToResponse(reservationToUpdate);
        response.setMessage("Update Successful");
        return response;
    }
    @Override
    public ReservationResponse checkIn(ReceiptRequest request){
        receiptService = new OHReceiptService();
        ReceiptResponse receipt = receiptService.findReceiptByEmail(request.getEmail());
        Receipt foundReceipt = Map.responseToReceipt(receipt);
        ReservationResponse response = new ReservationResponse();
        if(foundReceipt.isFullyPaidFor()){
            Reservation foundReservation = reservationRepository.findReservationByRoomNumber(request.getRoomNumber());
            if(foundReservation.getRoom().isAvailable()) foundReservation.getRoom().setAvailable(false);
            response = Map.reservationToReservationResponse(foundReservation);
            response.setMessage("Check In Successful");
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
    public DeleteResponse deleteReservationByRoomNumber(ReservationRequest request) {
        Reservation foundReservation = reservationRepository.findReservationByRoomNumber(request.getRoomNumberChosen());
        reservationRepository.deleteReservationByRoomNumber(foundReservation.getRoom().getRoomNumber());
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Reservation deleted successfully.");
        return response;
    }

    @Override
    public DeleteResponse deleteReservationById(int id)  {
        Reservation foundReservation = reservationRepository.findReservationById(id);
        reservationRepository.deleteReservationByRoomNumber(foundReservation.getId());
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Reservation deleted successfully.");
        return response;
    }
}
