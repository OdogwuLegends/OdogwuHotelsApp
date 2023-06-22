package odogwuHotels.controllers;

import odogwuHotels.data.models.*;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.AdminException;
import odogwuHotels.exceptions.EmailNotCorrectException;
import odogwuHotels.exceptions.EntityNotFoundException;
import odogwuHotels.myUtils.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static odogwuHotels.data.models.FindRoomByType.*;
import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

class AdminControllerTest {
    private final AdminController adminController = new AdminController();
    private final CustomerController customerController = new CustomerController();
    private AdminResponse superAdmin;
    private AdminResponse subAdmin;
    private RoomCreationResponse firstRoom;
    private RoomCreationResponse secondRoom;
    private RegisterUserResponse firstCustomer;
    private RegisterUserResponse secondCustomer;
    private ReservationResponse firstReservation;
    private ReservationResponse secondReservation;
    private ReceiptResponse firstReceipt;
    private ReceiptResponse secondReceipt;

    @BeforeEach
    void setUp(){
        superAdmin = adminController.registerSuperAdmin(buildSuperAdmin());
        subAdmin = adminController.registerAuxiliaryAdmins(buildSubAdmin(), Map.adminResponseToAdmin(superAdmin));
        firstRoom = adminController.createRoom(createFirstRoom());
        secondRoom = adminController.createRoom(createSecondRoom());
        firstCustomer = customerController.registerCustomer(createFirstCustomer());
        secondCustomer = customerController.registerCustomer(createSecondCustomer());
        firstReservation = adminController.makeReservation(createFirstReservation());
        secondReservation = adminController.makeReservation(createSecondReservation());

        try {
        firstReceipt = adminController.createReceipt(createFirstReservation(),Map.adminResponseToAdmin(superAdmin));
        } catch (AdminException ex){
            System.out.println(ex.getMessage());
        }

        try {
            secondReceipt = adminController.createReceipt(createSecondReservation(),Map.adminResponseToAdmin(superAdmin));
        } catch (AdminException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Test
    void registerSuperAdmin(){
        assertEquals("Super Admin Registered Successfully",superAdmin.getMessage());
    }
    @Test
    void registerAuxiliaryAdmin(){
        assertEquals("Auxiliary Admin Registered Successfully",subAdmin.getMessage());
    }
    @Test
    void editAdminDetails(){
        RequestToUpdateUserDetails request = new RequestToUpdateUserDetails();
        request.setEmail("dave@gmail.com");
        request.setPassword("6677");
        request.setNewEmail("batista@gmail.com");

        UpdateResponse updatedAdmin = new UpdateResponse();
        try {
            updatedAdmin = adminController.editAdminDetails(request);
        } catch (EmailNotCorrectException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals(subAdmin.getId(),updatedAdmin.getId());
        assertEquals(subAdmin.getAdminCode(),updatedAdmin.getAdminCode());
        assertEquals("batista@gmail.com",updatedAdmin.getEmail());
        assertEquals("6677",updatedAdmin.getPassword());
    }
    @Test
    void findAdminById(){
        AdminResponse foundAdmin = new AdminResponse();

        try {
            foundAdmin = adminController.findAdminById(superAdmin.getId());
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals(superAdmin.getEmail(),foundAdmin.getEmail());
        assertEquals(superAdmin.getId(),foundAdmin.getId());
    }
    @Test
    void findAllAdmins(){
        List<Admin> allAdmins = adminController.findAllAdmins();
        assertEquals(2,allAdmins.size());
        assertNotNull(allAdmins);
    }
    @Test
    void deleteAdminById(){
        DeleteResponse deletedAdmin = new DeleteResponse();

        try {
            deletedAdmin = adminController.deleteAdminById(subAdmin.getId());
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Admin Deleted Successfully",deletedAdmin.getMessage());
    }
    @Test
    void createRoom(){
        assertEquals("Room "+firstRoom.getRoomNumber()+" created successfully!",firstRoom.getMessage());
        assertEquals("Room "+secondRoom.getRoomNumber()+" created successfully!",secondRoom.getMessage());
    }
    @Test
    void findRoomById(){
        RoomSearchRequest request = new RoomSearchRequest();
        request.setRoomId(secondRoom.getRoomId());

        SearchResponse foundRoom = new SearchResponse();

        try {
            foundRoom = adminController.findRoomById(request);
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals(secondRoom.getPrice(),foundRoom.getPrice());
        assertEquals(secondRoom.getRoomType(),foundRoom.getRoomType());
    }
    @Test
    void findRoomByRoomNumber(){
        RoomSearchRequest request = new RoomSearchRequest();
        request.setRoomNumberChosen(1);

        SearchResponse foundRoom = new SearchResponse();

        try {
            foundRoom = adminController.findRoomByRoomNumber(request);
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals(firstRoom.getRoomId(),foundRoom.getRoomId());
        assertEquals(firstRoom.getRoomNumber(),foundRoom.getRoomNumber());
    }
    @Test
    void findAllRooms(){
        List<Room> allRooms = adminController.findAllRooms();
        assertNotNull(allRooms);
        assertEquals(2,allRooms.size());
    }
    @Test
    void seeAvailableRooms(){
        RequestToCreateRoom request = new RequestToCreateRoom();

        request.setRoomNumber(3);
        request.setRoomType(SINGLE);
        request.setPrice(BigDecimal.valueOf(50));
        request.setAvailable(true);
        adminController.createRoom(request);

        RoomSearchRequest searchRequest = new RoomSearchRequest();
        searchRequest.setFindRoomByType(ALL_ROOMS);

        SearchResponse availableRooms = adminController.seeAvailableRooms(searchRequest);
        assertEquals("All Available Rooms are Room(s) 1, 2, 3  ",availableRooms.getMessage());

        searchRequest.setFindRoomByType(SINGLE_ROOMS);
        availableRooms = adminController.seeAvailableRooms(searchRequest);
        assertEquals("Available Single Rooms are Room(s) 1, 3  ",availableRooms.getMessage());

        searchRequest.setFindRoomByType(DOUBLE_ROOMS);
        availableRooms = adminController.seeAvailableRooms(searchRequest);
        assertEquals("Available Double Rooms are Room(s) 2  ",availableRooms.getMessage());

    }
    @Test
    void seeBookedRooms() {
        RequestToCreateRoom request = new RequestToCreateRoom();
        request.setRoomNumber(3);
        request.setRoomType(SINGLE);
        request.setPrice(BigDecimal.valueOf(50));
        request.setAvailable(false);
        adminController.createRoom(request);

        RoomSearchRequest searchRequest = new RoomSearchRequest();
        searchRequest.setFindRoomByType(ALL_ROOMS);

        SearchResponse bookedRooms = adminController.seeBookedRooms(searchRequest);
        assertEquals("All Booked Rooms are Room(s) 3  ",bookedRooms.getMessage());

        searchRequest.setFindRoomByType(SINGLE_ROOMS);
        bookedRooms = adminController.seeBookedRooms(searchRequest);
        assertEquals("Booked Single Rooms are Room(s) 3  ",bookedRooms.getMessage());

        searchRequest.setFindRoomByType(DOUBLE_ROOMS);
        bookedRooms = adminController.seeBookedRooms(searchRequest);
        assertEquals("Booked Double Rooms are Room(s) => [Sorry, no room found.]",bookedRooms.getMessage());
    }
    @Test
    void editRoomDetails(){
        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomNumber(2);
        request.setRoomType(SINGLE);
        request.setPrice(BigDecimal.valueOf(50));
        request.setAvailable(false);

        UpdateResponse editedRoom = new UpdateResponse();
        try {
            editedRoom = adminController.editRoomDetails(request);
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Update Successful",editedRoom.getMessage());
    }
    @Test
    void deleteRoomById(){
        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomId(secondRoom.getRoomId());

        DeleteResponse deletedRoom = new DeleteResponse();
        try {
            deletedRoom = adminController.deleteRoomById(request);
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("Room "+deletedRoom.getRoomNumber()+" Deleted Successfully",deletedRoom.getMessage());
    }
    @Test
    void deleteRoomByRoomNumber(){
        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomNumber(firstRoom.getRoomNumber());

        DeleteResponse deletedRoom = new DeleteResponse();
        try {
            deletedRoom = adminController.deleteRoomByRoomNumber(request);
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("Room "+deletedRoom.getRoomNumber()+" Deleted Successfully",deletedRoom.getMessage());
    }
    @Test
    void findCustomerByEmail(){
        UserResponse foundCustomer = new UserResponse();
        try {
            foundCustomer = adminController.findCustomerByEmail(firstCustomer.getEmail());
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals(firstCustomer.getId(),foundCustomer.getId());
        assertEquals(firstCustomer.getLastName(),foundCustomer.getLastName());
    }
    @Test
    void findCustomerById(){
        UserResponse foundCustomer = new UserResponse();
        try {
            foundCustomer = adminController.findCustomerById(secondCustomer.getId());
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals(secondCustomer.getEmail(),foundCustomer.getEmail());
        assertEquals(secondCustomer.getPassword(),foundCustomer.getPassword());
    }
    @Test
    void findAllCustomers(){
        List<Customer> allCustomers = adminController.findAllCustomers();
        assertNotNull(allCustomers);
        assertEquals(2,allCustomers.size());
    }
    @Test
    void editCustomerDetails(){
        RequestToUpdateUserDetails request = new RequestToUpdateUserDetails();
        request.setEmail("vince@gmail.com");
        request.setFirstName("Shane");
        request.setPassword("0045");

        UpdateResponse editedCustomer = new UpdateResponse();
        try {
            editedCustomer = adminController.editCustomerDetails(request);
        }catch (EmailNotCorrectException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals(firstCustomer.getId(),editedCustomer.getId());
        assertEquals(firstCustomer.getLastName(),editedCustomer.getLastName());
        assertEquals("Shane",editedCustomer.getFirstName());
        assertEquals("0045",editedCustomer.getPassword());
    }
    @Test
    void deleteCustomerById(){
        DeleteResponse deletedCustomer = new DeleteResponse();
        try {
            deletedCustomer = adminController.deleteCustomerById(firstCustomer.getId());
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Customer Deleted Successfully",deletedCustomer.getMessage());
    }
    @Test
    void deleteCustomerByEmail(){
        DeleteResponse deletedCustomer = new DeleteResponse();
        try {
            deletedCustomer = adminController.deleteCustomerByEmail(secondCustomer.getEmail());
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Customer Deleted Successfully",deletedCustomer.getMessage());
    }
    @Test
    void makeReservation(){
        assertNotNull(firstReservation);
        assertEquals("Reservation successful.",firstReservation.getMessage());
        assertNotNull(secondReservation);
        assertEquals("Reservation successful.",secondReservation.getMessage());
    }
    @Test
    void findReservationById(){
        ReservationResponse foundReservation = new ReservationResponse();
        try {
            foundReservation = adminController.findReservationById(firstReservation.getId());
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals(firstReservation.getEmail(),foundReservation.getEmail());
        assertEquals(firstReservation.getCheckInDate(),foundReservation.getCheckInDate());
    }
    @Test
    void findReservationByRoomNumber(){
        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);

        ReservationResponse foundReservation = new ReservationResponse();
        try {
            foundReservation = adminController.findReservationByRoomNumber(request);
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals(secondReservation.getId(),foundReservation.getId());
        assertEquals(secondReservation.getCheckOutDate(),foundReservation.getCheckOutDate());
    }
    @Test
    void viewAllReservations(){
        List<Reservation> allReservations = adminController.viewAllReservations();
        assertNotNull(allReservations);
        assertEquals(2,allReservations.size());
    }
    @Test
    void editReservationDetails(){
        assertEquals(BigDecimal.valueOf(100),firstReservation.getRoomPrice());
        assertEquals("kevin@gmail.com",firstReservation.getEmail());
        assertEquals(DOUBLE,firstReservation.getRoomType());

        UpdateReservationRequest request = new UpdateReservationRequest();
        request.setRoomNumberChosen(2);
        request.setNewRoomNumberChosen(1);

        UpdateResponse editedReservation = adminController.editReservation(request);
        assertEquals("Update Successful",editedReservation.getMessage());
        assertEquals(firstReservation.getEmail(),editedReservation.getEmail());
        assertEquals(BigDecimal.valueOf(50),editedReservation.getRoomPrice());
        assertEquals(SINGLE,editedReservation.getRoomType());
    }
    @Test
    void deleteReservationByRoomNumber(){
        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(secondRoom.getRoomNumber());

        DeleteResponse deletedReservation = new DeleteResponse();
        try {
            deletedReservation = adminController.deleteReservationByRoomNumber(request);
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Reservation deleted successfully.",deletedReservation.getMessage());
    }
    @Test
    void deleteReservationById(){
        DeleteResponse deletedReservation = new DeleteResponse();
        try {
            deletedReservation = adminController.deleteReservationById(firstReservation.getId());
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Reservation deleted successfully.",deletedReservation.getMessage());
    }
    @Test
    void createReceipt(){
        assertEquals("Receipt Created",firstReceipt.getMessage());
        assertEquals("Receipt Created",secondReceipt.getMessage());
    }
    @Test
    void issueReceiptsById(){
        ReceiptResponse issuedReceipt = new ReceiptResponse();
        try {
            issuedReceipt = adminController.issueReceiptsById(firstReceipt.getId());
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }

        assertEquals("""
                \nNAME - Kevin Owen
                ROOM NUMBER - 2
                ROOM TYPE - DOUBLE
                AMOUNT PAID - $100
                BALANCE - 0
                CHECK IN DATE - 2023-06-28
                CHECK OUT DATE - 2023-07-02
                DURATION OF STAY - 4 days
                AUTHORIZED BY - Hunter Helms""",issuedReceipt.toString());
    }
    @Test
    void issueReceiptsByEmail(){
        ReceiptResponse issuedReceipt = new ReceiptResponse();
        try {
            issuedReceipt = adminController.issueReceiptsByEmail(secondReceipt.getEmail());
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }

        assertEquals("""
                \nNAME - Vince McMahon
                ROOM NUMBER - 1
                ROOM TYPE - SINGLE
                AMOUNT PAID - $150
                BALANCE - -100
                CHECK IN DATE - 2023-06-23
                CHECK OUT DATE - 2023-06-28
                DURATION OF STAY - 5 days
                AUTHORIZED BY - Hunter Helms""",issuedReceipt.toString());
    }
    @Test
    void findReceiptsById(){
        ReceiptResponse foundReceipt = new ReceiptResponse();
        try {
            foundReceipt = adminController.findReceiptById(secondReceipt.getId());
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals(secondReceipt.getCheckInDate(),foundReceipt.getCheckInDate());
        assertEquals(secondReceipt.getId(),foundReceipt.getId());
    }
    @Test
    void findAllReceipts(){
        List<Receipt> allReceipts = adminController.findAllReceipts();
        assertNotNull(allReceipts);
        assertEquals(2,allReceipts.size());
    }
    @Test
    void deleteReceiptById(){
        DeleteResponse deletedReceipt = new DeleteResponse();
        try {
            deletedReceipt = adminController.deleteReceiptById(secondReceipt.getId());
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("Receipt Deleted Successfully",deletedReceipt.getMessage());
    }






   private RegisterAdminRequest buildSuperAdmin(){
       RegisterAdminRequest request = new RegisterAdminRequest();

       request.setFirstName("Hunter");
       request.setLastName("Helms");
       request.setEmail("hhh@gmail.com");
       request.setPassword("2233");

       return request;
   }
    private RegisterAdminRequest buildSubAdmin(){
        RegisterAdminRequest request = new RegisterAdminRequest();

        request.setFirstName("Dave");
        request.setLastName("Batista");
        request.setEmail("dave@gmail.com");
        request.setPassword("2233");

        return request;
    }
    private RequestToCreateRoom createFirstRoom(){
        RequestToCreateRoom request = new RequestToCreateRoom();

        request.setRoomNumber(1);
        request.setRoomType(SINGLE);
        request.setPrice(BigDecimal.valueOf(50));
        request.setAvailable(true);

        return request;
    }
    private RequestToCreateRoom createSecondRoom(){
        RequestToCreateRoom request = new RequestToCreateRoom();

        request.setRoomNumber(2);
        request.setRoomType(DOUBLE);
        request.setPrice(BigDecimal.valueOf(100));
        request.setAvailable(true);

        return request;
    }
    private RegisterUserRequest createFirstCustomer(){
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Vince");
        request.setLastName("McMahon");
        request.setEmail("vince@gmail.com");
        request.setPassword("8976");

        return request;
    }
    private RegisterUserRequest createSecondCustomer(){
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Kevin");
        request.setLastName("Owen");
        request.setEmail("kevin@gmail.com");
        request.setPassword("1842");

        return request;
    }
    private ReservationRequest createFirstReservation(){
        ReservationRequest request = new ReservationRequest();

        request.setEmail("kevin@gmail.com");
        request.setRoomNumberChosen(2);
        request.setCheckInDate("28/06/2023");
        request.setCheckOutDate("02/07/2023");
        request.setMakePayment(BigDecimal.valueOf(100));

        return request;
    }
    private ReservationRequest createSecondReservation(){
        ReservationRequest request = new ReservationRequest();

        request.setEmail("vince@gmail.com");
        request.setRoomNumberChosen(1);
        request.setCheckInDate("23/06/2023");
        request.setCheckOutDate("28/06/2023");
        request.setMakePayment(BigDecimal.valueOf(150));

        return request;
    }

    @AfterEach
    void cleanUp(){
        adminController.deleteAllReceipts();
        adminController.deleteAllAdmins();
        adminController.deleteAllCustomers();
        adminController.deleteAllRooms();
        adminController.deleteAllReservations();
    }

}