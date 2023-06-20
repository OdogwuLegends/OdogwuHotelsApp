package odogwuHotels.controllers;

import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.EmailNotCorrectException;
import odogwuHotels.exceptions.EntityNotFoundException;
import odogwuHotels.services.CustomerService;
import odogwuHotels.services.OHCustomerService;

public class CustomerController {
    private final CustomerService customerService = new OHCustomerService();


    public RegisterUserResponse registerCustomer(RegisterUserRequest request){
        RegisterUserResponse newCustomer = new RegisterUserResponse();
        try {
            newCustomer = customerService.registerCustomer(request);
        }catch (EmailNotCorrectException ex){
            System.out.println(ex.getMessage());
        }
        return newCustomer;
    }
    public LoginResponse login(LoginRequest request) throws EntityNotFoundException{
        return customerService.login(request);
    }
    public UpdateResponse updateCustomerDetails(RequestToUpdateUserDetails request) throws EmailNotCorrectException {
        return customerService.updateCustomerDetails(request);
    }
    public SearchResponse findAvailableRooms(RoomSearchRequest request){
        return customerService.findAvailableRooms(request);
    }
    public ReservationResponse makeReservation(ReservationRequest request){
        return customerService.makeReservation(request);
    }
    public ReservationResponse findReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException {
        return customerService.findReservationByRoomNumber(request);
    }
    public UpdateResponse updateReservation(UpdateReservationRequest request){
        return customerService.updateReservation(request);
    }
    public DeleteResponse deleteReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException {
        return customerService.deleteReservationByRoomNumber(request);
    }
    public ReservationResponse checkIn(ReceiptRequest request) throws EntityNotFoundException{
        return customerService.checkIn(request);
    }
    public ReservationResponse checkOut(ReceiptRequest request){
        return customerService.checkOut(request);
    }
    public ReceiptResponse requestReceipt(ReceiptRequest request) throws EntityNotFoundException{
        return customerService.requestReceipt(request);
    }
    public FeedBackResponse giveFeedBack(String feedBack){
        return customerService.giveFeedBack(feedBack);
    }
}
