package odogwuHotels.services;

import odogwuHotels.data.models.*;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.AdminException;
import odogwuHotels.exceptions.EmailNotCorrectException;
import odogwuHotels.exceptions.EntityNotFoundException;

import java.util.List;

public interface AdminService {
    AdminResponse registerSuperAdmin(RegisterAdminRequest request) throws EmailNotCorrectException;
    AdminResponse registerAuxiliaryAdmins(RegisterAdminRequest request, Admin adminToApprove) throws EmailNotCorrectException;
    UpdateResponse editAdminDetails(RequestToUpdateUserDetails request) throws EmailNotCorrectException;
    AdminResponse findAdminById(int id) throws AdminException;
    List<Admin> findAllAdmins();
    DeleteResponse deleteAdminById(int id) throws AdminException;
    DeleteResponse deleteAllAdmins();
    ReservationResponse makeReservation(ReservationRequest request);
    ReservationResponse findReservationById(int id) throws EntityNotFoundException;
    ReservationResponse findReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException;
    List<Reservation> viewAllReservations();
    UpdateResponse editReservation(UpdateReservationRequest request);
    DeleteResponse deleteReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException;
    DeleteResponse deleteReservationById(int id) throws EntityNotFoundException;
    DeleteResponse deleteAllReservations();
    UserResponse findCustomerByEmail(String email) throws EntityNotFoundException;
    UserResponse findCustomerById(int id) throws EntityNotFoundException;
    List<Customer> findAllCustomers();
    UpdateResponse editCustomerDetails(RequestToUpdateUserDetails request) throws EmailNotCorrectException;
    DeleteResponse deleteCustomerById(int id) throws EntityNotFoundException;
    DeleteResponse deleteCustomerByEmail(String email) throws EntityNotFoundException;
    DeleteResponse deleteAllCustomers();
    RoomCreationResponse createRoom(RequestToCreateRoom createRoom);
    SearchResponse findRoomById(RoomSearchRequest request) throws EntityNotFoundException;
    SearchResponse findRoomByRoomNumber(RoomSearchRequest request) throws EntityNotFoundException;
    List<Room> findAllRooms();
    SearchResponse seeAvailableRooms(RoomSearchRequest request);
    SearchResponse seeBookedRooms(RoomSearchRequest request);
    UpdateResponse editRoomDetails(RequestToUpdateRoom updateRoom) throws EntityNotFoundException;
    DeleteResponse deleteRoomById(RequestToUpdateRoom updateRoom) throws EntityNotFoundException;
    DeleteResponse deleteRoomByRoomNumber(RequestToUpdateRoom updateRoom) throws EntityNotFoundException;
    DeleteResponse deleteAllRooms();
    void approvePayment(Receipt receipt);
    ReceiptResponse createReceipt(ReservationRequest request, Admin admin) throws AdminException;
    ReceiptResponse findReceiptById(int id) throws EntityNotFoundException;
    List<Receipt> findAllReceipts();
    DeleteResponse deleteReceiptById(int id) throws EntityNotFoundException;
    DeleteResponse deleteAllReceipts();
    ReceiptResponse issueReceiptsById(int id) throws EntityNotFoundException;
    ReceiptResponse issueReceiptsByEmail(String email) throws EntityNotFoundException;
    String respondToFeedBacks();

}
