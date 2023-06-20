package odogwuHotels.services;

import odogwuHotels.myUtils.Map;
import odogwuHotels.myUtils.Utils;
import odogwuHotels.data.models.*;
import odogwuHotels.data.repositories.AdminRepository;
import odogwuHotels.data.repositories.OHAdminRepository;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.AdminException;
import odogwuHotels.exceptions.EmailNotCorrectException;
import odogwuHotels.exceptions.EntityNotFoundException;

import java.util.List;

public class OHAdminService implements AdminService {
    private final AdminRepository adminRepository = OHAdminRepository.createObject();
    ReservationService reservationService;
    CustomerService customerService;
    RoomService roomService;
    ReceiptService receiptService;

    @Override
    public AdminResponse registerSuperAdmin(RegisterAdminRequest request) throws EmailNotCorrectException {
        if(!Utils.emailIsCorrect(request.getEmail())){
            throw new EmailNotCorrectException("Email not correct");
        }
        Admin superAdmin = Map.adminRequestToAdmin(request);
        superAdmin.setSuperAdmin(true);
        superAdmin.setApproveNewAdmin(true);
        Admin savedAdmin = adminRepository.saveAdmin(superAdmin);
        AdminResponse response = Map.adminToAdminResponse(savedAdmin);
        response.setMessage("Super Admin Registered Successfully");
        return response;
    }

    @Override
    public AdminResponse registerAuxiliaryAdmins(RegisterAdminRequest request, Admin adminToApprove) throws EmailNotCorrectException {
        if(!Utils.emailIsCorrect(request.getEmail())){
            throw new EmailNotCorrectException("Email not correct");
        }
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
    public UpdateResponse editAdminDetails(RequestToUpdateUserDetails request) throws EmailNotCorrectException {
        Admin adminToUpdate = adminRepository.findAdminByEmail(request.getEmail());
        int index = adminRepository.getIndex(adminToUpdate);

        if (adminToUpdate != null) {
            if (request.getFirstName() != null) adminToUpdate.setFirstName(request.getFirstName());
            if (request.getLastName() != null) adminToUpdate.setLastName(request.getLastName());
            if (request.getNewEmail() != null)
                if(!Utils.emailIsCorrect(request.getNewEmail())){
                    throw new EmailNotCorrectException("Email not correct");
                }
                adminToUpdate.setEmail(request.getNewEmail());
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
    public ReservationResponse findReservationById(int id) throws EntityNotFoundException {
        reservationService = new OHReservationService();
        return reservationService.findReservationById(id);
    }

    @Override
    public ReservationResponse findReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException {
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
    public DeleteResponse deleteReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException {
        reservationService = new OHReservationService();
        return reservationService.deleteReservationByRoomNumber(request);
    }

    @Override
    public DeleteResponse deleteReservationById(int id) throws EntityNotFoundException {
        reservationService = new OHReservationService();
        return reservationService.deleteReservationById(id);
    }

    @Override
    public UserResponse findCustomerByEmail(String email) throws EntityNotFoundException {
        customerService = new OHCustomerService();
        return customerService.findCustomerByEmail(email);
    }
    @Override
    public UserResponse findCustomerById(int id) throws EntityNotFoundException {
        customerService = new OHCustomerService();
        return customerService.findCustomerById(id);
    }

    @Override
    public List<Customer> findAllCustomers() {
        customerService = new OHCustomerService();
        return customerService.findAllCustomers();
    }

    @Override
    public UpdateResponse editCustomerDetails(RequestToUpdateUserDetails request) throws EmailNotCorrectException {
        customerService = new OHCustomerService();
        return customerService.updateCustomerDetails(request);
    }

    @Override
    public DeleteResponse deleteCustomerById(int id) throws EntityNotFoundException {
        customerService = new OHCustomerService();
        return customerService.deleteCustomerById(id);
    }

    public DeleteResponse deleteCustomerByEmail(String email) throws EntityNotFoundException {
        customerService = new OHCustomerService();
        return customerService.deleteCustomerByEmail(email);
    }

    @Override
    public RoomCreationResponse createRoom(RequestToCreateRoom createRoom) {
        roomService = new OHRoomService();
        return roomService.createRoom(createRoom);
    }

    @Override
    public SearchResponse findRoomById(RoomSearchRequest request) throws EntityNotFoundException {
        roomService = new OHRoomService();
        return roomService.findRoomByIdOrRoomNumber(request);
    }

    @Override
    public SearchResponse findRoomByRoomNumber(RoomSearchRequest request) throws EntityNotFoundException {
        roomService = new OHRoomService();
        return roomService.findRoomByIdOrRoomNumber(request);
    }

    @Override
    public List<Room> findAllRooms() {
        roomService = new OHRoomService();
        return roomService.findAllRooms();
    }

    @Override
    public SearchResponse seeAvailableRooms(RoomSearchRequest request) {
        roomService = new OHRoomService();
        return roomService.findAvailableRooms(request);
    }

    @Override
    public SearchResponse seeBookedRooms(RoomSearchRequest request) {
        roomService = new OHRoomService();
        return roomService.findBookedRooms(request);
    }

    @Override
    public UpdateResponse editRoomDetails(RequestToUpdateRoom updateRoom) throws EntityNotFoundException {
        roomService = new OHRoomService();
        return roomService.editRoomDetails(updateRoom);
    }

    @Override
    public DeleteResponse deleteRoomById(RequestToUpdateRoom request) throws EntityNotFoundException {
        roomService = new OHRoomService();
        return roomService.deleteRoomByRoomById(request);
    }

    @Override
    public DeleteResponse deleteRoomByRoomNumber(RequestToUpdateRoom updateRoom) throws EntityNotFoundException {
        roomService = new OHRoomService();
        return roomService.deleteRoomByRoomByRoomNumber(updateRoom);
    }

    @Override
    public void approvePayment(Receipt receipt) {
        //TAKE A RECEIPT ARGUMENT, FIND A SUPER ADMIN AND APPROVE THE PAYMENT PART OF THE RECEIPT
        //PASS THE SUPER ADMIN AS AN ARGUMENT TO...

    }

    @Override
    public ReceiptResponse createReceipt(ReservationRequest request, Admin admin) throws AdminException {
        receiptService = new OHReceiptService();
        return receiptService.createReceipt(request,admin);
    }

    @Override
    public ReceiptResponse issueReceiptsById(int id) throws EntityNotFoundException {
        receiptService = new OHReceiptService();
        return receiptService.generateReceiptById(id);
    }

    @Override
    public ReceiptResponse issueReceiptsByEmail(String email) throws EntityNotFoundException {
        receiptService = new OHReceiptService();
        return receiptService.generateReceiptByEmail(email);
    }

    @Override
    public ReceiptResponse findReceiptById(int id) throws EntityNotFoundException {
        receiptService = new OHReceiptService();
        return receiptService.findReceiptById(id);
    }

    @Override
    public List<Receipt> findAllReceipts() {
        receiptService = new OHReceiptService();
        return receiptService.findAllReceipts();
    }

    @Override
    public DeleteResponse deleteReceiptById(int id) throws EntityNotFoundException {
        receiptService = new OHReceiptService();
        return receiptService.deleteReceiptById(id);
    }


    @Override
    public String respondToFeedBacks() {
        //...
        return null;
    }

}