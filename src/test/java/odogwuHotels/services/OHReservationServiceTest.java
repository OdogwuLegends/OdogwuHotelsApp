package odogwuHotels.services;

import odogwuHotels.Map;
import odogwuHotels.Utils;
import odogwuHotels.data.models.Admin;
import odogwuHotels.data.models.Reservation;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.AdminResponse;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.ReservationResponse;
import odogwuHotels.dto.responses.UpdateResponse;
import odogwuHotels.exceptions.AdminException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

class OHReservationServiceTest {
    private final ReservationService reservationService = new OHReservationService();
    ReservationResponse firstReservation;
    ReservationResponse secondReservation;
    private final RoomService roomService = new OHRoomService();
    private final CustomerService customerService = new OHCustomerService();

    @BeforeEach
    void setUp(){
        customerService.registerCustomer(first());
        customerService.registerCustomer(second());

        roomService.createRoom(firstRoomCreated());
        roomService.createRoom(secondRoomCreated());

        firstReservation = reservationService.makeReservation(firstReservation());
        secondReservation = reservationService.makeReservation(secondReservation());
    }

    @Test
    void makeReservation(){
        assertEquals("Reservation successful.",firstReservation.getMessage());
        assertEquals("Reservation successful.",secondReservation.getMessage());
    }
    @Test
    void findReservationByRoomNumber(){
        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);
        ReservationResponse foundReservation = reservationService.findReservationByRoomNumber(request);

        assertEquals(firstReservation.getRoomNumber(),foundReservation.getRoomNumber());
    }
    @Test
    void findReservationById(){
        ReservationResponse foundReservation = reservationService.findReservationById(secondReservation.getId());

        assertEquals(secondReservation.getId(),foundReservation.getId());
    }
    @Test
    void updateReservation(){
        assertEquals(Utils.stringToLocalDate("12/06/2023"),firstReservation.getCheckInDate());
        UpdateReservationRequest request = new UpdateReservationRequest();
        request.setRoomNumberChosen(1);
        request.setCheckInDate("15/06/2023");

        UpdateResponse updatedReservation = reservationService.updateReservation(request);
        assertEquals("15/06/2023",updatedReservation.getCheckInDate());
        assertEquals(firstReservation.getFirstName(),updatedReservation.getFirstName());
        assertEquals(firstReservation.getId(),updatedReservation.getId());
    }
    @Test
    void updateReservationAndRoomDetailsChanges(){
        UpdateReservationRequest request = new UpdateReservationRequest();
        request.setRoomNumberChosen(1);
        request.setNewRoomNumberChosen(2);
        assertEquals(BigDecimal.valueOf(50),firstReservation.getRoomPrice());

        UpdateResponse updatedReservation = reservationService.updateReservation(request);
        assertEquals(BigDecimal.valueOf(100),updatedReservation.getRoomPrice());
        assertEquals(firstReservation.getEmail(),updatedReservation.getEmail());
    }
    @Test
    void checkIn(){
        ReservationResponse beforeCheckIn = secondReservation;
        Reservation reservation = Map.reservationResponseToReservation(beforeCheckIn);
        assertTrue(reservation.getRoom().isAvailable());

        Admin admin = Map.adminResponseToAdmin(superAdmin());
        ReceiptService receiptService = new OHReceiptService();

        try {
            receiptService.createReceipt(secondReservation(),admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }

        ReceiptRequest request = new ReceiptRequest();
        request.setRoomNumber(2);
        request.setEmail("ned@gmail.com");

        ReservationResponse afterCheckIn = reservationService.checkIn(request);
        assertEquals("Check In Successful",afterCheckIn.getMessage());
        reservation = Map.reservationResponseToReservation(afterCheckIn);
        assertFalse(reservation.getRoom().isAvailable());
        assertEquals(beforeCheckIn.getLastName(),afterCheckIn.getLastName());
    }
    @Test
    void checkOut(){
        ReservationResponse beforeCheckIn = reservationService.makeReservation(firstReservation());
        Reservation reservation = Map.reservationResponseToReservation(beforeCheckIn);
        assertTrue(reservation.getRoom().isAvailable());

        Admin admin = Map.adminResponseToAdmin(superAdmin());
        ReceiptService receiptService = new OHReceiptService();

        try {
            receiptService.createReceipt(firstReservation(),admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }


        ReceiptRequest request = new ReceiptRequest();
        request.setEmail("legend@gmail.com");
        request.setRoomNumber(1);

        ReservationResponse afterCheckIn = reservationService.checkIn(request);
        assertEquals("Check In Successful",afterCheckIn.getMessage());

        request.setRoomNumber(1);
        ReservationResponse afterCheckOut = reservationService.checkOut(request);
        assertEquals("Check Out Successful",afterCheckOut.getMessage());
        reservation = Map.reservationResponseToReservation(afterCheckOut);
        assertTrue(reservation.getRoom().isAvailable());
    }
    @Test
    void findAllReservations(){
        List<Reservation> allReservations = reservationService.findAllReservations();
        assertEquals(2,allReservations.size());
    }
    @Test
    void deleteReservationByRoomNumber(){
        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(2);
        DeleteResponse deletedRoom = reservationService.deleteReservationByRoomNumber(request);
        assertEquals("Reservation deleted successfully.",deletedRoom.getMessage());
    }
    @Test
    void deleteReservationById(){
        DeleteResponse deletedRoom = reservationService.deleteReservationById(firstReservation.getId());
        assertEquals("Reservation deleted successfully.",deletedRoom.getMessage());
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
    private ReservationRequest firstReservation(){
        ReservationRequest request = new ReservationRequest();

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

        request.setEmail("ned@gmail.com");
        request.setRoomNumberChosen(2);
        request.setRoomType(DOUBLE);
        request.setCheckInDate("22/06/2023");
        request.setCheckOutDate("25/06/2023");
        request.setMakePayment(BigDecimal.valueOf(100));

        return request;
    }
    private AdminResponse superAdmin(){
        AdminService adminService = new OHAdminService();
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setFirstName("Jay-Jay");
        request.setLastName("Okocha");
        request.setPassword("1999");
        request.setEmail("jayjay@gmail.com");

        return adminService.registerSuperAdmin(request);
    }
}