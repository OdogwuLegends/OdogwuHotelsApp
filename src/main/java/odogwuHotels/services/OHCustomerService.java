//package odogwuHotels.services;
//
//import odogwuHotels.Map;
//import odogwuHotels.data.models.Customer;
//import odogwuHotels.data.repositories.CustomerRepository;
//import odogwuHotels.data.repositories.OHCustomerRepository;
//import odogwuHotels.dto.requests.*;
//import odogwuHotels.dto.responses.*;
//
//import java.util.Objects;
//
//public class OHCustomerService implements CustomerService {
//    private final CustomerRepository customerRepository = new OHCustomerRepository();
//    private final RoomService roomService = new OHRoomService();
//    private final ReservationService reservationService = new OHReservationService();
//    private final PaymentService paymentService = new OHPaymentService();
//
//    @Override
//    public RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request) {
//        customerRepository.saveCustomer(Map.customerRequestToCustomer(request));
//        RegisterCustomerResponse response = new RegisterCustomerResponse();
//        response.setMessage("Registration Successful");
//        return response;
//    }
//
//    @Override
//    public LoginResponse login(LoginRequest request) {
//        Customer foundCustomer =  customerRepository.findCustomerByEmail(request.getEmail());
//        LoginResponse response = new LoginResponse();
//        if(Objects.equals(foundCustomer.getPassword(),request.getPassword())) response.setLoggedIn(true);
//        return response;
//    }
//    public CustomerResponse findCustomerByEmail(String email){
//        Customer foundCustomer = customerRepository.findCustomerByEmail(email);
//        return Map.customerToCustomerResponse(foundCustomer);
//    }
//
//    @Override
//    public RoomSearchResponse findAvailableRooms(RoomSearchRequest request) {
//        return roomService.findAvailableRooms(request);
//    }
//
//    @Override
//    public ReservationResponse makeReservation(ReservationRequest request) {
//        return reservationService.makeReservation(request);
//    }
//
//    @Override
//    public ReservationResponse findReservationByRoomNumber(ReservationRequest request) {
//        return reservationService.findReservationByRoomNumber(request);
//    }
//
//    @Override
//    public DeleteResponse deleteReservationByRoomNumber(ReservationRequest request) {
//        return reservationService.deleteReservationByRoomNumber(request);
//    }
//
//    @Override
//    public ReceiptResponse requestReceipt(ReceiptRequest request) {
//        return paymentService.generateReceipt();
//    }
//}
