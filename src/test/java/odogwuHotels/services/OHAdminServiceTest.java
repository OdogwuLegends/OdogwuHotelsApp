package odogwuHotels.services;

import odogwuHotels.myUtils.Map;
import odogwuHotels.data.models.*;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.AdminException;
import odogwuHotels.exceptions.EmailNotCorrectException;
import odogwuHotels.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static odogwuHotels.data.models.FindRoomByChoice.ALL_ROOMS;
import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

class OHAdminServiceTest {
    private final AdminService adminService = new OHAdminService();
    private final ReservationService reservationService = new OHReservationService();
    private final RoomService roomService = new OHRoomService();
    private final CustomerService customerService = new OHCustomerService();
    private final ReceiptService receiptService = new OHReceiptService();
    private AdminResponse superAdmin;
    private AdminResponse auxAdmin;
    private RegisterUserResponse firstCustomer;
    private RegisterUserResponse secondCustomer;
    private RoomCreationResponse firstRoom;
    private RoomCreationResponse secondRoom;
    private ReceiptResponse firstReceipt;
    private ReceiptResponse secondReceipt;

    @BeforeEach
    void setUp(){
        try {
        firstCustomer = customerService.registerCustomer(firstCustomer());
        }catch (EmailNotCorrectException ex){
            System.out.println(ex.getMessage());
        }

        try {
        secondCustomer = customerService.registerCustomer(secondCustomer());
        }catch (EmailNotCorrectException ex){
            System.out.println(ex.getMessage());
        }

        firstRoom = roomService.createRoom(firstRoomCreated());
        secondRoom = roomService.createRoom(secondRoomCreated());

        try {
        superAdmin = adminService.registerSuperAdmin(mainAdmin());
        }catch (EmailNotCorrectException ex){
            System.out.println(ex.getMessage());
        }

        try {
        auxAdmin = adminService.registerAuxiliaryAdmins(subAdmin(), Map.adminResponseToAdmin(superAdmin));
        }catch (EmailNotCorrectException ex){
            System.out.println(ex.getMessage());
        }

        reservationService.makeReservation(firstReservation());
        reservationService.makeReservation(secondReservation());

        try {
            firstReceipt = receiptService.createReceipt(firstReservation(),Map.adminResponseToAdmin(superAdmin));
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        try {
            secondReceipt = receiptService.createReceipt(secondReservation(),Map.adminResponseToAdmin(superAdmin));
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }

    }

    @Test
    void registerSuperAdmin(){
        Admin firstAdmin = Map.adminResponseToAdmin(superAdmin);
        Admin secondAdmin = Map.adminResponseToAdmin(auxAdmin);
        assertTrue(firstAdmin.isSuperAdmin());
        assertFalse(secondAdmin.isSuperAdmin());
    }
    @Test
    void registerAuxAdmin(){
        Admin secondAdmin = Map.adminResponseToAdmin(auxAdmin);
        assertFalse(secondAdmin.isSuperAdmin());
    }
    @Test
    void adminCannotRegisterWithWrongEmail(){
        RegisterAdminRequest request = new RegisterAdminRequest();

        request.setFirstName("Ric");
        request.setLastName("Flair");
        request.setEmail("flair@gmailcom");
        request.setPassword("1976");
    }
    @Test
    void editAdminDetails(){
        assertEquals("Inem",auxAdmin.getFirstName());
        assertEquals("ename@gmail.com",auxAdmin.getEmail());

        RequestToUpdateUserDetails request = new RequestToUpdateUserDetails();
        request.setEmail("ename@gmail.com");
        request.setFirstName("Inyang");
        request.setNewEmail("hello@gmail.com");

        UpdateResponse updatedDetails = new UpdateResponse();
        try {
            updatedDetails = adminService.editAdminDetails(request);
        } catch (EmailNotCorrectException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals(auxAdmin.getId(),updatedDetails.getId());
        assertEquals(auxAdmin.getAdminCode(),updatedDetails.getAdminCode());
        assertEquals("hello@gmail.com",updatedDetails.getEmail());
        assertEquals("Inyang",updatedDetails.getFirstName());
    }
    @Test
    void findAdminById(){
        AdminResponse foundAdmin = new AdminResponse();
        try {
          foundAdmin  = adminService.findAdminById(superAdmin.getId());
        } catch (AdminException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals(superAdmin.getId(),foundAdmin.getId());
        assertEquals(superAdmin.getEmail(),foundAdmin.getEmail());
    }
    @Test
    void errorIfFoundAdminIsNull(){
        Admin newAdmin = new Admin();
        AdminResponse foundAdmin = Map.adminToAdminResponse(newAdmin);

        assertThrows(AdminException.class,()-> adminService.findAdminById(foundAdmin.getId()));
    }
    @Test
    void findAllAdmins(){
        List<Admin> allAdmins = adminService.findAllAdmins();
        assertEquals(2,allAdmins.size());
    }
    @Test
    void deleteAdminById(){
        DeleteResponse deletedAdmin = new DeleteResponse();
        try {
            deletedAdmin = adminService.deleteAdminById(auxAdmin.getId());
        } catch (AdminException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("Admin Deleted Successfully",deletedAdmin.getMessage());
    }
    @Test
    void nullAdminCannotBeDeleted(){
        Admin newAdmin = new Admin();
        AdminResponse adminToBeDeleted = Map.adminToAdminResponse(newAdmin);
        assertThrows(AdminException.class,()-> adminService.deleteAdminById(adminToBeDeleted.getId()));
    }
    @Test
    void makeReservation(){
        ReservationResponse reservation = adminService.makeReservation(firstReservation());
        assertEquals("Reservation successful.",reservation.getMessage());
    }
    @Test
    void findReservationById(){
        ReservationService reservationService = new OHReservationService();
        ReservationResponse savedReservation = reservationService.makeReservation(firstReservation());
        ReservationResponse foundReservation = new ReservationResponse();
        try {
            foundReservation = adminService.findReservationById(savedReservation.getId());
        }catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }

        assertEquals("""
                \nNAME - Legend Odogwu
                ROOM NUMBER - 1
                ROOM TYPE - SINGLE
                CHECK IN DATE - 2023-06-12
                CHECK OUT DATE - 2023-06-18""",foundReservation.toString());
    }
    @Test
    void findReservationByRoomNumber(){
        ReservationService reservationService = new OHReservationService();
        reservationService.makeReservation(firstReservation());

        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);
        ReservationResponse foundReservation = new ReservationResponse();
        try {
            foundReservation = adminService.findReservationByRoomNumber(request);
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }

        assertEquals("""
                \nNAME - Legend Odogwu
                ROOM NUMBER - 1
                ROOM TYPE - SINGLE
                CHECK IN DATE - 2023-06-12
                CHECK OUT DATE - 2023-06-18""",foundReservation.toString());
    }
    @Test
    void viewAllReservations(){
        ReservationService reservationService = new OHReservationService();
        reservationService.makeReservation(firstReservation());

        List<Reservation> allReservations = adminService.viewAllReservations();
        assertEquals(1,allReservations.size());
    }
    @Test
    void editReservation(){
        ReservationService reservationService = new OHReservationService();
        ReservationResponse savedReservation = reservationService.makeReservation(firstReservation());
        assertEquals("Odogwu",savedReservation.getLastName());

        UpdateReservationRequest request = new UpdateReservationRequest();
        request.setRoomNumberChosen(1);
        request.setLastName("Batista");

        UpdateResponse updatedReservation = adminService.editReservation(request);
        assertEquals("Batista",updatedReservation.getLastName());
        assertEquals(savedReservation.getId(),updatedReservation.getId());
    }
    @Test
    void deleteReservationByRoomNumber(){
        ReservationService reservationService = new OHReservationService();
        reservationService.makeReservation(firstReservation());

        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);
        DeleteResponse toBeDeleted = new DeleteResponse();
        try {
            toBeDeleted = adminService.deleteReservationByRoomNumber(request);
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }

        assertEquals("Reservation deleted successfully.",toBeDeleted.getMessage());
    }
    @Test
    void deleteReservationById(){
        ReservationService reservationService = new OHReservationService();
        ReservationResponse toBeDeleted = reservationService.makeReservation(firstReservation());

        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);
        DeleteResponse deleted = new DeleteResponse();
        try {
            deleted = adminService.deleteReservationById(toBeDeleted.getId());
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("Reservation deleted successfully.",deleted.getMessage());
    }
    @Test
    void findCustomerByEmail(){
        UserResponse foundCustomer = new UserResponse();
        try {
            foundCustomer = adminService.findCustomerByEmail(firstCustomer.getEmail());
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("Odogwu",foundCustomer.getLastName());
        assertEquals(1,foundCustomer.getId());
    }
    @Test
    void findCustomerById(){
        UserResponse foundCustomer = new UserResponse();
        try {
            foundCustomer = adminService.findCustomerById(secondCustomer.getId());
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("5678",foundCustomer.getPassword());
        assertEquals(2,foundCustomer.getId());
    }
    @Test
    void findAllCustomers(){
        List<Customer> allCustomers = adminService.findAllCustomers();
        assertEquals(2,allCustomers.size());
        assertFalse(allCustomers.isEmpty());
    }
    @Test
    void editCustomerDetails(){
        assertEquals("5678",secondCustomer.getPassword());
        RequestToUpdateUserDetails request = new RequestToUpdateUserDetails();
        request.setEmail("ned@gmail.com");
        request.setPassword("9876");

        UpdateResponse updatedDetails = new UpdateResponse();
        try {
            updatedDetails = adminService.editCustomerDetails(request);
        }catch (EmailNotCorrectException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("9876",updatedDetails.getPassword());
        assertEquals(secondCustomer.getId(),updatedDetails.getId());
        assertEquals(secondCustomer.getFirstName(),updatedDetails.getFirstName());
    }
    @Test
    void deleteCustomerById(){
        DeleteResponse foundCustomer = new DeleteResponse();
        try {
            foundCustomer = adminService.deleteCustomerById(firstCustomer.getId());
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Customer Deleted Successfully",foundCustomer.getMessage());
    }
    @Test
    void deleteCustomerByEmail(){
        DeleteResponse foundCustomer = new DeleteResponse();
        try {
            foundCustomer = adminService.deleteCustomerByEmail("ned@gmail.com");
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Customer Deleted Successfully",foundCustomer.getMessage());
    }
    @Test
    void errorIfCustomerIsNull(){
        DeleteResponse foundCustomer = new DeleteResponse();
        assertThrows(EntityNotFoundException.class,()-> adminService.deleteCustomerByEmail(foundCustomer.getEmail()));
    }
    @Test
    void createRoom(){
        assertEquals("Room "+firstRoom.getRoomNumber()+" created successfully!",firstRoom.getMessage());
        assertEquals("Room "+secondRoom.getRoomNumber()+" created successfully!",secondRoom.getMessage());
    }
    @Test
    void findRoomById(){
        RoomSearchRequest request = new RoomSearchRequest();
        request.setRoomId(firstRoom.getRoomId());

        SearchResponse foundRoom = new SearchResponse();
        try {
            foundRoom = adminService.findRoomById(request);
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("Room "+foundRoom.getRoomId()+" found.",foundRoom.getMessage());
        assertEquals(firstRoom.getRoomNumber(),foundRoom.getRoomNumber());
    }
    @Test
    void findRoomByRoomNumber(){
        RoomSearchRequest request = new RoomSearchRequest();
        request.setRoomNumberChosen(2);

        SearchResponse foundRoom = new SearchResponse();
        try {
            foundRoom = adminService.findRoomByRoomNumber(request);
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("Room "+foundRoom.getRoomNumber()+" found.",foundRoom.getMessage());
        assertEquals(secondRoom.getRoomNumber(),foundRoom.getRoomNumber());
    }
    @Test
    void findAllRooms(){
        List<Room> allRooms = adminService.findAllRooms();
        assertNotNull(allRooms);
        assertEquals(2,allRooms.size());
    }
    @Test
    void seeAllAvailableRooms(){
        RoomSearchRequest request = new RoomSearchRequest();
        request.setFindRoomByChoice(ALL_ROOMS);
        SearchResponse availableRooms = adminService.seeAvailableRooms(request);
        assertEquals("All Available Rooms are Room(s) 1, 2  ",availableRooms.getMessage());
    }
    @Test
    void seeAllBookedRooms(){
        RoomSearchRequest request = new RoomSearchRequest();
        request.setFindRoomByChoice(ALL_ROOMS);
        SearchResponse bookedRooms = adminService.seeBookedRooms(request);
        assertEquals("All Booked Rooms are Room(s) => [Sorry, no room found.]",bookedRooms.getMessage());
    }
    @Test
    void editRoomDetails(){
        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomNumber(2);
        request.setRoomType(SINGLE);
        request.setPrice(BigDecimal.valueOf(100));
        request.setAvailable(false);

        UpdateResponse editedRoom = new UpdateResponse();
        try {
            editedRoom = adminService.editRoomDetails(request);
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("Update Successful",editedRoom.getMessage());
        assertEquals(BigDecimal.valueOf(100),editedRoom.getRoomPrice());
        assertFalse(editedRoom.isAvailable());
        assertEquals(secondRoom.getRoomNumber(),editedRoom.getRoomNumberChosen());
    }
    @Test
    void deleteRoomById(){
        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomId(secondRoom.getRoomId());

        DeleteResponse deletedRoom = new DeleteResponse();
        try {
            deletedRoom = adminService.deleteRoomById(request);
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Room "+deletedRoom.getRoomNumber()+" Deleted Successfully",deletedRoom.getMessage());
    }
    @Test
    void deleteRoomByRoomNumber(){
        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomNumber(firstRoom.getRoomNumber());

        DeleteResponse deletedRoom = new DeleteResponse();
        try {
            deletedRoom = adminService.deleteRoomByRoomNumber(request);
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Room "+deletedRoom.getRoomNumber()+" Deleted Successfully",deletedRoom.getMessage());
    }
    @Test
    void createReceipt(){
        Admin admin = Map.adminResponseToAdmin(superAdmin);
        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);
        request.setEmail("legend@gmail.com");

        ReceiptResponse firstReceipt = new ReceiptResponse();
        try {
            firstReceipt = receiptService.createReceipt(request,admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Receipt Created",firstReceipt.getMessage());
    }
    @Test
    void issueReceiptsById(){
        ReceiptResponse foundReceipt = new ReceiptResponse();
        try {
            foundReceipt = adminService.issueReceiptsById(firstReceipt.getId());
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("""
                \nNAME - Legend Odogwu
                ROOM NUMBER - 1
                ROOM TYPE - SINGLE
                AMOUNT PAID - $50
                BALANCE - 0
                CHECK IN DATE - 2023-06-12
                CHECK OUT DATE - 2023-06-18
                DURATION OF STAY - 6 days
                AUTHORIZED BY - Legend Max""",foundReceipt.toString());
    }
    @Test
    void issueReceiptsByEmail(){
        ReceiptResponse foundReceipt = new ReceiptResponse();
        try {
            foundReceipt = adminService.issueReceiptsByEmail(secondReceipt.getEmail());
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("""
                \nNAME - Ned Stark
                ROOM NUMBER - 2
                ROOM TYPE - DOUBLE
                AMOUNT PAID - $60
                BALANCE - 40
                CHECK IN DATE - 2023-06-18
                CHECK OUT DATE - 2023-06-20
                DURATION OF STAY - 2 days
                AUTHORIZED BY - Legend Max""",foundReceipt.toString());
    }
    @Test
    void findReceiptById(){
        ReceiptResponse foundReceipt = new ReceiptResponse();
        try {
            foundReceipt = adminService.findReceiptById(secondReceipt.getId());
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }

        assertEquals(secondReceipt.getLastName(),foundReceipt.getLastName());
        assertEquals(secondReceipt.getBalance(),foundReceipt.getBalance());
    }
    @Test
    void findAllReceipts(){
        List<Receipt> allReceipts = adminService.findAllReceipts();
        assertEquals(2,allReceipts.size());
    }
    @Test
    void deleteReceiptById(){
        DeleteResponse deletedReceipt = new DeleteResponse();
        try {
            deletedReceipt = adminService.deleteReceiptById(firstReceipt.getId());
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("Receipt Deleted Successfully",deletedReceipt.getMessage());

        List<Receipt> allReceipts = adminService.findAllReceipts();
        assertEquals(1,allReceipts.size());
    }











    private RegisterAdminRequest mainAdmin(){
        RegisterAdminRequest request = new RegisterAdminRequest();

        request.setFirstName("Legend");
        request.setLastName("Max");
        request.setEmail("leg@gmail.com");
        request.setPassword("1234");

        return request;
    }
    private RegisterAdminRequest subAdmin(){
        RegisterAdminRequest request = new RegisterAdminRequest();

        request.setFirstName("Inem");
        request.setLastName("Udo");
        request.setEmail("ename@gmail.com");
        request.setPassword("2222");

        return request;
    }
    private ReservationRequest firstReservation(){
        ReservationRequest request = new ReservationRequest();

        request.setFirstName("Legend");
        request.setLastName("Odogwu");
        request.setEmail("legend@gmail.com");
        request.setRoomNumberChosen(1);
        request.setRoomType(SINGLE);
        request.setCheckInDate("12/06/2023");
        request.setCheckOutDate("18/06/2023");
        request.setMakePayment(BigDecimal.valueOf(50));

        return request;
    }
    private ReservationRequest secondReservation(){
        ReservationRequest request = new ReservationRequest();

        request.setFirstName("Ned");
        request.setLastName("Stark");
        request.setEmail("ned@gmail.com");
        request.setRoomNumberChosen(2);
        request.setRoomType(DOUBLE);
        request.setCheckInDate("18/06/2023");
        request.setCheckOutDate("20/06/2023");
        request.setMakePayment(BigDecimal.valueOf(60));

        return request;
    }
    private RegisterUserRequest firstCustomer(){
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Legend");
        request.setLastName("Odogwu");
        request.setEmail("legend@gmail.com");
        request.setPassword("1234");

        return request;
    }
    private RegisterUserRequest secondCustomer(){
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Ned");
        request.setLastName("Stark");
        request.setEmail("ned@gmail.com");
        request.setPassword("5678");

        return request;
    }
    private RequestToCreateRoom firstRoomCreated(){
        RequestToCreateRoom request = new RequestToCreateRoom();

        request.setRoomNumber(1);
        request.setRoomType(SINGLE);
        request.setPrice(BigDecimal.valueOf(50));
        request.setAvailable(true);

        return request;
    }
    private RequestToCreateRoom secondRoomCreated(){
        RequestToCreateRoom request = new RequestToCreateRoom();

        request.setRoomNumber(2);
        request.setRoomType(DOUBLE);
        request.setPrice(BigDecimal.valueOf(100));
        request.setAvailable(true);

        return request;
    }

}