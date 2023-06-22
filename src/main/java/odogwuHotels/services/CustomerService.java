package odogwuHotels.services;

import odogwuHotels.data.models.Customer;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.EmailNotCorrectException;
import odogwuHotels.exceptions.EntityNotFoundException;

import java.util.List;

public interface CustomerService {
    RegisterUserResponse registerCustomer(RegisterUserRequest request) throws EmailNotCorrectException;
    LoginResponse login(LoginRequest request) throws EntityNotFoundException;
    UserResponse findCustomerByEmail(String email) throws EntityNotFoundException;
    UserResponse findCustomerById(int id) throws EntityNotFoundException;
    List<Customer> findAllCustomers();
    UpdateResponse updateCustomerDetails(RequestToUpdateUserDetails request) throws EmailNotCorrectException;
    DeleteResponse deleteCustomerByEmail(String email) throws EntityNotFoundException;
    DeleteResponse deleteCustomerById(int id) throws EntityNotFoundException;
    DeleteResponse deleteAll();
    SearchResponse findAvailableRooms(RoomSearchRequest request);
    ReservationResponse makeReservation(ReservationRequest request);
    ReservationResponse findReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException;
    UpdateResponse updateReservation(UpdateReservationRequest request);
    DeleteResponse deleteReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException;
    ReservationResponse checkIn(ReceiptRequest request) throws EntityNotFoundException;
    ReservationResponse checkOut(ReceiptRequest request);
    ReceiptResponse requestReceipt(ReceiptRequest request) throws EntityNotFoundException;
    FeedBackResponse giveFeedBack(String feedBack);
}
