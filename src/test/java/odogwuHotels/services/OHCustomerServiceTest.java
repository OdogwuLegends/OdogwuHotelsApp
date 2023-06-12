package odogwuHotels.services;

import odogwuHotels.Map;
import odogwuHotels.Utils;
import odogwuHotels.data.models.*;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

class OHCustomerServiceTest {
    private final CustomerService customerService = new OHCustomerService();
    private final RoomService roomService = new OHRoomService();
    private RegisterUserResponse firstCustomer;
    private RegisterUserResponse secondCustomer;

    @BeforeEach
    void setUp(){
        firstCustomer = customerService.registerCustomer(first());
        secondCustomer = customerService.registerCustomer(second());

        roomService.createRoom(firstSingleRoomCreated());
        roomService.createRoom(secondSingleRoomCreated());
    }

    @Test
    void registerCustomer(){
        assertNotNull(firstCustomer);
        assertNotNull(secondCustomer);
        assertEquals("Registration Successful",firstCustomer.getMessage());
        assertEquals("Registration Successful",secondCustomer.getMessage());
    }
    @Test
    void loginWorks(){
        LoginRequest request = new LoginRequest();
        request.setEmail("ned@gmail.com");
        request.setPassword("5678");

        LoginResponse loginIsCorrect = new LoginResponse();
        try {
            loginIsCorrect = customerService.login(request);
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }

        assertTrue(loginIsCorrect.isLoggedIn());
    }
    @Test
    void loginFailsWithWrongEmail(){
        LoginRequest request = new LoginRequest();
        request.setEmail("neddy@gmail.com");
        request.setPassword("5678");
        LoginResponse loginIsCorrect = new LoginResponse();
        try {
            loginIsCorrect = customerService.login(request);
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        assertFalse(loginIsCorrect.isLoggedIn());
    }

    @Test
    void loginFailsWithWrongPassword(){
        LoginRequest request = new LoginRequest();
        request.setEmail("legend@gmail.com");
        request.setPassword("9922");
        LoginResponse loginIsCorrect = new LoginResponse();
        try {
            loginIsCorrect = customerService.login(request);
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Password Incorrect",loginIsCorrect.getMessage());
    }
    @Test
    void findCustomerByEmail(){
        UserResponse foundCustomer = new UserResponse();
        try {
            foundCustomer = customerService.findCustomerByEmail("legend@gmail.com");
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        assertEquals(firstCustomer.getEmail(),foundCustomer.getEmail());
        assertEquals(firstCustomer.getPassword(),foundCustomer.getPassword());
    }
    @Test
    void findCustomerById(){
        UserResponse foundCustomer = new UserResponse();
        try {
            foundCustomer = customerService.findCustomerById(secondCustomer.getId());
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        assertEquals(secondCustomer.getLastName(),foundCustomer.getLastName());
        assertEquals(secondCustomer.getId(),foundCustomer.getId());
    }

    @Test
    void findAllCustomers(){
        List<Customer> allCustomers = customerService.findAllCustomers();
        assertEquals(2,allCustomers.size());
    }
    @Test
    void updateCustomerDetails(){
        assertEquals("Ned",secondCustomer.getFirstName());
        assertEquals("Stark",secondCustomer.getLastName());

        RequestToUpdateUserDetails request = new RequestToUpdateUserDetails();
        request.setEmail("ned@gmail.com");
        request.setFirstName("David");
        request.setLastName("Munroe");

        secondCustomer.setFirstName(request.getFirstName());
        secondCustomer.setLastName(request.getLastName());

        UpdateResponse updatedCustomer = customerService.updateCustomerDetails(request);
        assertEquals(secondCustomer.getFirstName(),updatedCustomer.getFirstName());
        assertEquals("David",secondCustomer.getFirstName());
        assertEquals("Munroe",secondCustomer.getLastName());
        assertEquals("Update Successful",updatedCustomer.getMessage());
    }
    @Test
    void deleteCustomerByEmail(){
        DeleteResponse customerToDelete = customerService.deleteCustomerByEmail("legend@gmail.com");
        assertEquals("Customer Deleted Successfully",customerToDelete.getMessage());
    }
    @Test
    void deleteCustomerById(){
        DeleteResponse customerToDelete = customerService.deleteCustomerById(secondCustomer.getId());
        assertEquals("Customer Deleted Successfully",customerToDelete.getMessage());
    }
    @Test
    void customerCanFindAvailableRooms(){
        RoomService roomService = new OHRoomService();
        roomService.createRoom(firstSingleRoomCreated());
        roomService.createRoom(secondSingleRoomCreated());

        RoomSearchRequest request = new RoomSearchRequest();
        request.setFindRoomByChoice(FindRoomByChoice.ALL_ROOMS);
        SearchResponse rooms = customerService.findAvailableRooms(request);

        assertEquals("All Available Rooms are Room(s) 1, 2  ",rooms.getMessage());
    }

    @Test
    void customerCanMakeReservation(){
        ReservationResponse reservation = customerService.makeReservation(makeReservation());
        assertEquals("Reservation successful.",reservation.getMessage());
    }
    @Test
    void customerCanFindReservation(){
        customerService.makeReservation(makeReservation());

        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);

        ReservationResponse foundReservation = customerService.findReservationByRoomNumber(request);
        System.out.println(foundReservation);
        assertEquals("""
                \nNAME - Legend Odogwu
                ROOM NUMBER - 1
                ROOM TYPE - SINGLE
                CHECK IN DATE - 2023-06-12
                CHECK OUT DATE - 2023-06-18""",foundReservation.toString());
    }
    @Test
    void customerCanUpdateReservation(){
        customerService.makeReservation(makeReservation());

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setRoomNumberChosen(1);
        ReservationResponse foundReservation = customerService.findReservationByRoomNumber(reservationRequest);
        assertEquals("12/06/2023", Utils.localDateToString(foundReservation.getCheckInDate()));


        UpdateReservationRequest updateRequest = new UpdateReservationRequest();
        updateRequest.setRoomNumberChosen(1);
        updateRequest.setCheckInDate("25/06/2023");

        UpdateResponse updatedReservation = customerService.updateReservation(updateRequest);
        assertEquals("25/06/2023", updatedReservation.getCheckInDate());
        assertEquals(foundReservation.getRoomNumber(),updatedReservation.getRoomNumberChosen());
    }
    @Test
    void customerCanDeleteReservationByRoomNumber(){
        customerService.makeReservation(makeReservation());

        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);
        DeleteResponse deletedReservation = customerService.deleteReservationByRoomNumber(request);
        assertEquals("Reservation deleted successfully.",deletedReservation.getMessage());
    }
    @Test
    void customerCanGenerateReceipt(){
        customerService.makeReservation(makeReservation());
        Admin admin = Map.adminResponseToAdmin(superAdmin());

        ReceiptService receiptService = new OHReceiptService();
        receiptService.createReceipt(makeReservation(),admin);

        ReceiptRequest request = new ReceiptRequest();
        request.setEmail("legend@gmail.com");
        ReceiptResponse foundReceipt = customerService.requestReceipt(request);
        assertEquals("""
                \nNAME - Legend Odogwu
                ROOM NUMBER - 1
                ROOM TYPE - SINGLE
                AMOUNT PAID - $50
                BALANCE - 0
                CHECK IN DATE - 2023-06-12
                CHECK OUT DATE - 2023-06-18
                DURATION OF STAY - 6 days
                AUTHORIZED BY - Inem Udo""",foundReceipt.toString());
    }
    @Test
    void customerCanCheckIn(){
        ReservationResponse beforeCheckIn = customerService.makeReservation(makeReservation());
        Reservation reservation = Map.reservationResponseToReservation(beforeCheckIn);
        assertTrue(reservation.getRoom().isAvailable());

        Admin admin = Map.adminResponseToAdmin(superAdmin());
        ReceiptService receiptService = new OHReceiptService();
        receiptService.createReceipt(makeReservation(),admin);

        ReceiptRequest request = new ReceiptRequest();
        request.setEmail("legend@gmail.com");
        request.setRoomNumber(1);

        ReservationResponse afterCheckIn = customerService.checkIn(request);
        assertEquals("Check In Successful",afterCheckIn.getMessage());
        reservation = Map.reservationResponseToReservation(afterCheckIn);
        assertFalse(reservation.getRoom().isAvailable());
        assertEquals(beforeCheckIn.getRoomNumber(),afterCheckIn.getRoomNumber());
    }
    @Test
    void customerCanCheckOut(){
        ReservationResponse beforeCheckIn = customerService.makeReservation(makeReservation());
        Reservation reservation = Map.reservationResponseToReservation(beforeCheckIn);
        assertTrue(reservation.getRoom().isAvailable());

        Admin admin = Map.adminResponseToAdmin(superAdmin());
        ReceiptService receiptService = new OHReceiptService();
        receiptService.createReceipt(makeReservation(),admin);

        ReceiptRequest request = new ReceiptRequest();
        request.setEmail("legend@gmail.com");
        request.setRoomNumber(1);

        ReservationResponse afterCheckIn = customerService.checkIn(request);
        assertEquals("Check In Successful",afterCheckIn.getMessage());
        reservation = Map.reservationResponseToReservation(afterCheckIn);
        assertFalse(reservation.getRoom().isAvailable());

        request.setRoomNumber(1);
        ReservationResponse afterCheckOut = customerService.checkOut(request);
        assertEquals("Check Out Successful",afterCheckOut.getMessage());
        reservation = Map.reservationResponseToReservation(afterCheckOut);
        assertTrue(reservation.getRoom().isAvailable());

    }













    private RegisterUserRequest first(){
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Legend");
        request.setLastName("Odogwu");
        request.setEmail("legend@gmail.com");
        request.setPassword("1234");

        return request;
    }
    private RegisterUserRequest second(){
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Ned");
        request.setLastName("Stark");
        request.setEmail("ned@gmail.com");
        request.setPassword("5678");

        return request;
    }
    private RequestToCreateRoom secondSingleRoomCreated(){
        RequestToCreateRoom request = new RequestToCreateRoom();

        request.setRoomNumber(2);
        request.setRoomType(DOUBLE);
        request.setPrice(BigDecimal.valueOf(50));
        request.setAvailable(true);

        return request;
    }
    private RequestToCreateRoom firstSingleRoomCreated(){
        RequestToCreateRoom request = new RequestToCreateRoom();

        request.setRoomNumber(1);
        request.setRoomType(SINGLE);
        request.setPrice(BigDecimal.valueOf(50));
        request.setAvailable(true);

        return request;
    }
    private ReservationRequest makeReservation(){
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
    private AdminResponse superAdmin(){
        AdminService adminService = new OHAdminService();
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setFirstName("Inem");
        request.setLastName("Udo");
        request.setPassword("3333");
        request.setEmail("ename@gmail.com");

        return adminService.registerSuperAdmin(request);
    }


    @AfterEach
          void cleanUp(){
        //customerService.deleteCustomerById(firstCustomer.getId());
        //customerService.deleteCustomerById(secondCustomer.getId());
        //customerService.deleteCustomerByEmail(firstCustomer.getEmail());
        //customerService.deleteCustomerByEmail(secondCustomer.getEmail());
    }
}