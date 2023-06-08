//package odogwuHotels.services;
//
//import odogwuHotels.Map;
//import odogwuHotels.data.models.Customer;
//import odogwuHotels.data.models.Reservation;
//import odogwuHotels.data.models.Room;
//import odogwuHotels.data.repositories.OHReservationRepository;
//import odogwuHotels.data.repositories.ReservationRepository;
//import odogwuHotels.dto.requests.ReservationRequest;
//import odogwuHotels.dto.responses.CustomerResponse;
//import odogwuHotels.dto.responses.DeleteResponse;
//import odogwuHotels.dto.responses.ReservationResponse;
//import odogwuHotels.dto.responses.RoomSearchResponse;
//
//import java.util.List;
//
//public class OHReservationService implements ReservationService{
//    private final ReservationRepository reservationRepository = new OHReservationRepository();
//    private final RoomService roomService = new OHRoomService();
//    private final CustomerService customerService = new OHCustomerService();
//    @Override
//    public ReservationResponse makeReservation(ReservationRequest request) {
//        RoomSearchResponse room = roomService.findRoomByIdOrRoomNumber(Map.reservationReqToRoomSearchResponse(request));
//        Room roomToReserve = Map.roomSearchResponseToRoom(room);
//        CustomerResponse customerResponse = customerService.findCustomerByEmail(request.getEmail());
//        Customer foundCustomer = Map.customerResponseToCustomer(customerResponse);
//        reservationRepository.saveReservation(Map.reservationReqToReservation(request,foundCustomer,roomToReserve));
//        ReservationResponse response = new ReservationResponse();
//        response.setMessage("Reservation successful.");
//        return response;
//    }
//    @Override
//    public ReservationResponse findReservationByRoomNumber(ReservationRequest request) {
//        Reservation foundReservation = reservationRepository.findReservationByRoomNumber(request.getRoomNumberChosen());
//        return Map.reservationToReservationResponse(foundReservation);
//    }
//
//    @Override
//    public ReservationResponse findReservationById(ReservationRequest request) {
//
//        return ;
//    }
//    @Override
//    public String checkIn(ReservationRequest request){
//        Reservation foundReservation = reservationRepository.findReservationByRoomNumber(request.getRoomNumberChosen());
//        if(foundReservation.getRoom().isAvailable()) foundReservation.getRoom().setAvailable(false);
//        return "Check In Successful";
//    }
//    @Override
//    public String checkOut(ReservationRequest request){
//        Reservation foundReservation = reservationRepository.findReservationByRoomNumber(request.getRoomNumberChosen());
//        if(!foundReservation.getRoom().isAvailable()) foundReservation.getRoom().setAvailable(true);
//        return "Check Out Successful";
//    }
//    @Override
//    public List<Reservation> findAllReservations() {
//        return reservationRepository.findAllReservations();
//    }
//
//    @Override
//    public DeleteResponse deleteReservationByRoomNumber(ReservationRequest request) {
//        reservationRepository.deleteReservationByRoomNumber(request.getRoomNumberChosen());
//        DeleteResponse response = new DeleteResponse();
//        response.setMessage("Reservation deleted successfully.");
//        return response;
//    }
//
//    @Override
//    public DeleteResponse deleteReservationById(ReservationRequest request) {
//
//        return ;
//    }
//}
