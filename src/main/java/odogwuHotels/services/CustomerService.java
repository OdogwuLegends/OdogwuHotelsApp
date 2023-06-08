package odogwuHotels.services;

import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;

public interface CustomerService {
    RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request);
    LoginResponse login(LoginRequest request);
    CustomerResponse findCustomerByEmail(String email);
    RoomSearchResponse findAvailableRooms(RoomSearchRequest request);
    ReservationResponse makeReservation(ReservationRequest request);
    ReservationResponse findReservationByRoomNumber(ReservationRequest request);
    DeleteResponse deleteReservationByRoomNumber(ReservationRequest request);
    ReceiptResponse requestReceipt(ReceiptRequest request);
}
