package odogwuHotels.controllers;

import odogwuHotels.myUtils.Map;
import odogwuHotels.myUtils.Utils;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.AdminException;
import odogwuHotels.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static odogwuHotels.data.models.FindRoomByChoice.ALL_ROOMS;
import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

class CustomerControllerTest {
    private final CustomerController customerController = new CustomerController();
    private final AdminController adminController = new AdminController();
    private RegisterUserResponse firstCustomer;
    private ReservationResponse madeReservation;

    @BeforeEach
    void setUp(){
        firstCustomer = customerController.registerCustomer(first());
        adminController.createRoom(firstRoom());
        adminController.createRoom(secondRoom());
        madeReservation = customerController.makeReservation(reservation());

        try {
        adminController.createReceipt(reservation(), Map.adminResponseToAdmin(superAdmin()));
        } catch (AdminException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Test
    void registerCustomer(){
        assertEquals("Registration Successful",firstCustomer.getMessage());
    }
    @Test
    void customerCanLogin(){
        LoginRequest request = new LoginRequest();
        request.setEmail("van@gmail.com");
        request.setPassword("1234");

        LoginResponse loggedIn = new LoginResponse();
        try {
            loggedIn = customerController.login(request);
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertTrue(loggedIn.isLoggedIn());
    }
    @Test
    void customerCanEditDetails(){
        RequestToUpdateUserDetails request = new RequestToUpdateUserDetails();
        request.setEmail("van@gmail.com");
        request.setFirstName("Jean-Claude");
        request.setLastName("Van-Damme");

        UpdateResponse updatedCustomer = customerController.updateCustomerDetails(request);
        assertEquals(firstCustomer.getId(),updatedCustomer.getId());
        assertEquals(firstCustomer.getEmail(),updatedCustomer.getEmail());
        assertEquals("Van-Damme",updatedCustomer.getLastName());
    }
    @Test
    void customerCanFindAvailableRooms(){
        RoomSearchRequest request = new RoomSearchRequest();
        request.setFindRoomByChoice(ALL_ROOMS);
        SearchResponse availableRooms = customerController.findAvailableRooms(request);
        assertEquals("All Available Rooms are Room(s) 1, 2  ",availableRooms.getMessage());
    }
    @Test
    void customerCanMakeReservation(){
        assertEquals("Reservation successful.",madeReservation.getMessage());
    }
    @Test
    void customerCanFindReservationByRoomNumber(){
        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(2);
        ReservationResponse foundReservation = new ReservationResponse();
        try {
            foundReservation = customerController.findReservationByRoomNumber(request);
        }catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }

        assertEquals("""
                \nNAME - Jean Claude
                ROOM NUMBER - 2
                ROOM TYPE - DOUBLE
                CHECK IN DATE - 2023-06-22
                CHECK OUT DATE - 2023-06-26""",foundReservation.toString());
    }
    @Test
    void customerCanEditReservation(){
        assertEquals("22/06/2023", Utils.localDateToString(madeReservation.getCheckInDate()));
        assertEquals("26/06/2023", Utils.localDateToString(madeReservation.getCheckOutDate()));

        UpdateReservationRequest request = new UpdateReservationRequest();
        request.setRoomNumberChosen(2);
        request.setCheckInDate("25/06/2023");
        request.setCheckOutDate("28/06/2023");

        UpdateResponse editedReservation = customerController.updateReservation(request);
        assertEquals("Update Successful",editedReservation.getMessage());
        assertEquals(madeReservation.getId(),editedReservation.getId());
        assertEquals(madeReservation.getEmail(),editedReservation.getEmail());
        assertEquals("25/06/2023",editedReservation.getCheckInDate());
        assertEquals("28/06/2023",editedReservation.getCheckOutDate());
    }
    @Test
    void customerCanDeleteReservationByRoomNumber(){
        ReservationRequest request = new ReservationRequest();
        request.setEmail("van@gmail.com");
        request.setRoomNumberChosen(2);

        DeleteResponse deletedReservation = new DeleteResponse();
        try {
            deletedReservation = customerController.deleteReservationByRoomNumber(request);
        }catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("Reservation deleted successfully.",deletedReservation.getMessage());
    }
    @Test
    void customerCanCheckIn(){
        ReceiptRequest request = new ReceiptRequest();
        request.setEmail("van@gmail.com");
        request.setRoomNumber(2);

        ReservationResponse checkIn = new ReservationResponse();
        try {
            checkIn = customerController.checkIn(request);
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Check In Successful",checkIn.getMessage());
    }
    @Test
    void customerCanCheckOut(){
        ReceiptRequest request = new ReceiptRequest();
        request.setEmail("van@gmail.com");
        request.setRoomNumber(2);

        ReservationResponse checkIn = new ReservationResponse();
        try {
            checkIn = customerController.checkIn(request);
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Check In Successful",checkIn.getMessage());


        request.setRoomNumber(2);
        ReservationResponse afterCheckOut = customerController.checkOut(request);
        assertEquals("Check Out Successful",afterCheckOut.getMessage());
    }
    @Test
    void customerCanRequestReceipt(){
        ReceiptRequest request = new ReceiptRequest();
        request.setRoomNumber(2);
        request.setEmail("van@gmail.com");

        ReceiptResponse foundReceipt = new ReceiptResponse();
        try {
            foundReceipt = customerController.requestReceipt(request);
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("""
                \nNAME - Jean Claude
                ROOM NUMBER - 2
                ROOM TYPE - DOUBLE
                AMOUNT PAID - $200
                BALANCE - -100
                CHECK IN DATE - 2023-06-22
                CHECK OUT DATE - 2023-06-26
                DURATION OF STAY - 4 days
                AUTHORIZED BY - Daniel Bryan""",foundReceipt.toString());
    }







    private RegisterUserRequest first(){
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("Jean");
        request.setLastName("Claude");
        request.setEmail("van@gmail.com");
        request.setPassword("1234");

        return request;
    }
    private RequestToCreateRoom firstRoom(){
        RequestToCreateRoom request = new RequestToCreateRoom();

        request.setRoomNumber(1);
        request.setRoomType(SINGLE);
        request.setPrice(BigDecimal.valueOf(50));
        request.setAvailable(true);

        return request;
    }
    private RequestToCreateRoom secondRoom(){
        RequestToCreateRoom request = new RequestToCreateRoom();

        request.setRoomNumber(2);
        request.setRoomType(DOUBLE);
        request.setPrice(BigDecimal.valueOf(100));
        request.setAvailable(true);

        return request;
    }
    private ReservationRequest reservation(){
        ReservationRequest request = new ReservationRequest();

        request.setFirstName("Jean");
        request.setLastName("Claude");
        request.setEmail("van@gmail.com");
        request.setRoomNumberChosen(2);
        request.setRoomType(DOUBLE);
        request.setCheckInDate("22/06/2023");
        request.setCheckOutDate("26/06/2023");
        request.setMakePayment(BigDecimal.valueOf(200));

        return request;
    }
    private AdminResponse superAdmin(){
        RegisterAdminRequest request = new RegisterAdminRequest();

        request.setFirstName("Daniel");
        request.setLastName("Bryan");
        request.setEmail("yes@gmail.com");
        request.setPassword("2345");

        return adminController.registerSuperAdmin(request);
    }

}