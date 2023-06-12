package odogwuHotels.services;

import odogwuHotels.data.models.*;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.UserNotFoundException;

import java.util.List;

public interface AdminService {
    AdminResponse registerSuperAdmin(RegisterAdminRequest request);
    AdminResponse registerAuxiliaryAdmins(RegisterAdminRequest request, Admin adminToApprove);
    UpdateResponse editAdminDetails(RequestToUpdateUserDetails request);
    AdminResponse findAdminById(int id);
    List<Admin> findAllAdmins();
    DeleteResponse deleteAdminById(int id);
    ReservationResponse makeReservation(ReservationRequest request);
    ReservationResponse findReservationById(int id);
    ReservationResponse findReservationByRoomNumber(ReservationRequest request);
    List<Reservation> viewAllReservations();
    UpdateResponse editReservation(UpdateReservationRequest request);
    DeleteResponse deleteReservationByRoomNumber(ReservationRequest request);
    UserResponse findCustomerByEmail(String email) throws UserNotFoundException;
    List<Customer> findAllCustomers();
    UpdateResponse editCustomerDetails(RequestToUpdateUserDetails request);
    DeleteResponse deleteCustomerById(int id);
    DeleteResponse deleteCustomerByEmail(String email);
    RoomCreationResponse createRoom(RequestToCreateRoom createRoom);
    SearchResponse findRoomById(RoomSearchRequest request);
    SearchResponse findRoomByRoomNumber(RoomSearchRequest request);
    List<Room> findAllRooms();
    UpdateResponse editRoomDetails(RequestToUpdateRoom updateRoom);
    DeleteResponse deleteRoomByRoomById(RequestToUpdateRoom updateRoom);
    void approvePayment(Receipt receipt);
    ReceiptResponse findReceiptById(int id);
    List<Receipt> findAllReceipts();
    DeleteResponse deleteReceiptById(int id);
    ReceiptResponse issueReceiptsById(int id);
    String respondToFeedBacks();

}
