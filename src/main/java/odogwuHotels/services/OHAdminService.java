package odogwuHotels.services;

import odogwuHotels.Map;
import odogwuHotels.data.models.*;
import odogwuHotels.data.repositories.AdminRepository;
import odogwuHotels.data.repositories.OHAdminRepository;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.AdminException;
import odogwuHotels.exceptions.UserNotFoundException;

import java.util.List;

public class OHAdminService implements AdminService {
    private final AdminRepository adminRepository = new OHAdminRepository();
    ReservationService reservationService;
    CustomerService customerService;
    RoomService roomService;
    ReceiptService receiptService;

    @Override
    public AdminResponse registerSuperAdmin(RegisterAdminRequest request) {
        Admin superAdmin = Map.adminRequestToAdmin(request);
        superAdmin.setSuperAdmin(true);
        superAdmin.setApproveNewAdmin(true);
        Admin savedAdmin = adminRepository.saveAdmin(superAdmin);
        AdminResponse response = Map.adminToAdminResponse(savedAdmin);
        response.setMessage("Super Admin Registered Successfully");
        return response;
    }

    @Override
    public AdminResponse registerAuxiliaryAdmins(RegisterAdminRequest request, Admin adminToApprove) {
        Admin newAdmin = Map.adminRequestToAdmin(request);
        AdminResponse response = new AdminResponse();
        if (adminToApprove.isSuperAdmin()) {
            newAdmin.setApproveNewAdmin(true);
            newAdmin.setSuperAdmin(false);
            Admin savedAdmin = adminRepository.saveAdmin(newAdmin);
            response = Map.adminToAdminResponse(savedAdmin);
            response.setMessage("Auxiliary Admin Registered Successfully");
        } else {
            response.setMessage("Registration Not Successful");
        }
        return response;
    }

    @Override
    public UpdateResponse editAdminDetails(RequestToUpdateUserDetails request) {
        Admin adminToUpdate = adminRepository.findAdminByEmail(request.getEmail());
        int index = adminRepository.getIndex(adminToUpdate);

        if (adminToUpdate != null) {
            if (request.getFirstName() != null) adminToUpdate.setFirstName(request.getFirstName());
            if (request.getLastName() != null) adminToUpdate.setLastName(request.getLastName());
            if (request.getNewEmail() != null) adminToUpdate.setEmail(request.getNewEmail());
            if (request.getPassword() != null) adminToUpdate.setPassword(request.getPassword());
            if (request.isSuperAdmin() != adminToUpdate.isSuperAdmin())
                adminToUpdate.setSuperAdmin(request.isSuperAdmin());
        }

        Admin updatedAdmin = adminRepository.updateDetails(index, adminToUpdate);
        UpdateResponse response = Map.adminToUpdateResponse(updatedAdmin);
        response.setMessage("Update Successful");
        return response;
    }

    @Override
    public AdminResponse findAdminById(int id) throws AdminException {
        Admin foundAdmin = adminRepository.findAdminById(id);
        if(foundAdmin == null){
            throw new AdminException("Admin not found");
        }
        return Map.adminToAdminResponse(foundAdmin);
    }

    @Override
    public List<Admin> findAllAdmins() {
        return adminRepository.findAllAdmins();
    }

    @Override
    public DeleteResponse deleteAdminById(int id) throws AdminException {
        Admin foundAdmin = adminRepository.findAdminById(id);
        if(foundAdmin == null){
            throw new AdminException("Admin not found");
        }
        adminRepository.deleteAdminById(foundAdmin.getId());
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Admin Deleted Successfully");
        return response;
    }

    @Override
    public ReservationResponse makeReservation(ReservationRequest request) {
        reservationService = new OHReservationService();
        return reservationService.makeReservation(request);
    }

    @Override
    public ReservationResponse findReservationById(int id) {
        reservationService = new OHReservationService();
        return reservationService.findReservationById(id);
    }

    @Override
    public ReservationResponse findReservationByRoomNumber(ReservationRequest request) {
        reservationService = new OHReservationService();
        return reservationService.findReservationByRoomNumber(request);
    }

    @Override
    public List<Reservation> viewAllReservations() {
        reservationService = new OHReservationService();
        return reservationService.findAllReservations();
    }

    @Override
    public UpdateResponse editReservation(UpdateReservationRequest request) {
        reservationService = new OHReservationService();
        return reservationService.updateReservation(request);
    }

    @Override
    public DeleteResponse deleteReservationByRoomNumber(ReservationRequest request) {
        reservationService = new OHReservationService();
        return reservationService.deleteReservationByRoomNumber(request);
    }

    @Override
    public UserResponse findCustomerByEmail(String email) throws UserNotFoundException {
        customerService = new OHCustomerService();
        return customerService.findCustomerByEmail(email);
    }

    @Override
    public List<Customer> findAllCustomers() {
        customerService = new OHCustomerService();
        return customerService.findAllCustomers();
    }

    @Override
    public UpdateResponse editCustomerDetails(RequestToUpdateUserDetails request) {
        customerService = new OHCustomerService();
        return customerService.updateCustomerDetails(request);
    }

    @Override
    public DeleteResponse deleteCustomerById(int id) {
        customerService = new OHCustomerService();
        return customerService.deleteCustomerById(id);
    }

    public DeleteResponse deleteCustomerByEmail(String email) {
        customerService = new OHCustomerService();
        return customerService.deleteCustomerByEmail(email);
    }

    @Override
    public RoomCreationResponse createRoom(RequestToCreateRoom createRoom) {
        roomService = new OHRoomService();
        return roomService.createRoom(createRoom);
    }

    @Override
    public SearchResponse findRoomById(RoomSearchRequest request) {
        roomService = new OHRoomService();
        return roomService.findRoomByIdOrRoomNumber(request);
    }

    @Override
    public SearchResponse findRoomByRoomNumber(RoomSearchRequest request) {
        roomService = new OHRoomService();
        return roomService.findRoomByIdOrRoomNumber(request);
    }

    @Override
    public List<Room> findAllRooms() {
        roomService = new OHRoomService();
        return roomService.findAllRooms();
    }

    @Override
    public UpdateResponse editRoomDetails(RequestToUpdateRoom updateRoom) {
        roomService = new OHRoomService();
        return roomService.editRoomDetails(updateRoom);
    }

    @Override
    public DeleteResponse deleteRoomByRoomById(RequestToUpdateRoom updateRoom) {
        roomService = new OHRoomService();
        return roomService.deleteRoomByRoomById(updateRoom);
    }

    @Override
    public void approvePayment(Receipt receipt) {
        //TAKE A RECEIPT ARGUMENT, FIND A SUPER ADMIN AND APPROVE THE PAYMENT PART OF THE RECEIPT
        //PASS THE SUPER ADMIN AS AN ARGUMENT TO...

    }

    @Override
    public ReceiptResponse findReceiptById(int id) {
        receiptService = new OHReceiptService();
        return receiptService.findReceiptById(id);
    }

    @Override
    public List<Receipt> findAllReceipts() {
        receiptService = new OHReceiptService();
        return receiptService.findAllReceipts();
    }

    @Override
    public DeleteResponse deleteReceiptById(int id) {
        receiptService = new OHReceiptService();
        return receiptService.deleteReceiptById(id);
    }

    @Override
    public ReceiptResponse issueReceiptsById(int id) {
        receiptService = new OHReceiptService();
        return receiptService.generateReceiptById(id);
    }

    @Override
    public String respondToFeedBacks() {
        //...
        return null;
    }

}