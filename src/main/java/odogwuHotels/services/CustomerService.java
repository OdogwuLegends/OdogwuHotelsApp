package odogwuHotels.services;

import odogwuHotels.data.models.Customer;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.UserNotFoundException;

import java.util.List;

public interface CustomerService {
    RegisterUserResponse registerCustomer(RegisterUserRequest request);
    LoginResponse login(LoginRequest request) throws UserNotFoundException;
    UserResponse findCustomerByEmail(String email) throws UserNotFoundException;
    UserResponse findCustomerById(int id) throws UserNotFoundException;
    List<Customer> findAllCustomers();
    UpdateResponse updateCustomerDetails(RequestToUpdateUserDetails request);
    DeleteResponse deleteCustomerByEmail(String email);
    DeleteResponse deleteCustomerById(int id);
    SearchResponse findAvailableRooms(RoomSearchRequest request);
    ReservationResponse makeReservation(ReservationRequest request);
    ReservationResponse findReservationByRoomNumber(ReservationRequest request);
    UpdateResponse updateReservation(UpdateReservationRequest request);
    DeleteResponse deleteReservationByRoomNumber(ReservationRequest request);
    ReservationResponse checkIn(ReceiptRequest request);
    ReservationResponse checkOut(ReceiptRequest request);
    ReceiptResponse requestReceipt(ReceiptRequest request);
}
