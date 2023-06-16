package odogwuHotels.services;

import odogwuHotels.Map;
import odogwuHotels.data.models.Admin;
import odogwuHotels.data.models.Receipt;
import odogwuHotels.dto.requests.RegisterAdminRequest;
import odogwuHotels.dto.requests.RegisterUserRequest;
import odogwuHotels.dto.requests.RequestToCreateRoom;
import odogwuHotels.dto.requests.ReservationRequest;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.AdminException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

class OHReceiptServiceTest {
    private final ReceiptService receiptService = new OHReceiptService();
    private final ReservationService reservationService = new OHReservationService();
    private final AdminService adminService = new OHAdminService();
    private final RoomService roomService = new OHRoomService();
    private final CustomerService customerService = new OHCustomerService();
    private AdminResponse superAdmin;
    private AdminResponse auxAdmin;

    @BeforeEach
    void setUp(){
        customerService.registerCustomer(firstCustomer());
        customerService.registerCustomer(secondCustomer());

        roomService.createRoom(firstRoomCreated());
        roomService.createRoom(secondRoomCreated());

        superAdmin = adminService.registerSuperAdmin(superAdmin());
        auxAdmin = adminService.registerAuxiliaryAdmins(auxiliaryAdmin(), Map.adminResponseToAdmin(superAdmin));

        reservationService.makeReservation(first());
        reservationService.makeReservation(second());
    }

    @Test
    void createReceipt(){
        Admin admin = Map.adminResponseToAdmin(superAdmin);
        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);
        request.setEmail("curry@gmail.com");

        ReceiptResponse firstReceipt = new ReceiptResponse();
        try {
            firstReceipt = receiptService.createReceipt(request,admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Receipt Created",firstReceipt.getMessage());

        request.setEmail("mikej@gmail.com");
        request.setRoomNumberChosen(2);

        ReceiptResponse secondReceipt = new ReceiptResponse();
        try {
            secondReceipt = receiptService.createReceipt(request,admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Receipt Created",secondReceipt.getMessage());
    }
    @Test
    void methodThrowsExceptionOnCreateReceipt(){
        Admin admin = Map.adminResponseToAdmin(auxAdmin);
        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);
        request.setEmail("curry@gmail.com");

      assertThrows(AdminException.class, ()-> receiptService.createReceipt(request,admin));
    }
    @Test
    void findReceiptById(){
        Admin admin = Map.adminResponseToAdmin(superAdmin);
        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);
        request.setEmail("curry@gmail.com");

        ReceiptResponse firstReceipt = new ReceiptResponse();
        try {
            firstReceipt = receiptService.createReceipt(request,admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Receipt Created",firstReceipt.getMessage());


        ReceiptResponse foundReceipt = receiptService.findReceiptById(firstReceipt.getId());
        assertEquals("Receipt Found",foundReceipt.getMessage());
    }
    @Test
    void findReceiptByEmail(){
        Admin admin = Map.adminResponseToAdmin(superAdmin);
        ReservationRequest request = new ReservationRequest();
        request.setEmail("mikej@gmail.com");
        request.setRoomNumberChosen(2);

        ReceiptResponse secondReceipt = new ReceiptResponse();
        try {
            secondReceipt = receiptService.createReceipt(request,admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Receipt Created",secondReceipt.getMessage());


        ReceiptResponse foundReceipt = receiptService.findReceiptByEmail(secondReceipt.getEmail());
        assertEquals("Receipt Found",foundReceipt.getMessage());
    }
    @Test
    void generateReceiptById(){
        Admin admin = Map.adminResponseToAdmin(superAdmin);
        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);
        request.setEmail("curry@gmail.com");

        ReceiptResponse firstReceipt = new ReceiptResponse();
        try {
            firstReceipt = receiptService.createReceipt(request,admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Receipt Created",firstReceipt.getMessage());

        ReceiptResponse generateReceipt = receiptService.generateReceiptById(firstReceipt.getId());
        assertEquals("""
                \nNAME - Steph Curry
                ROOM NUMBER - 1
                ROOM TYPE - SINGLE
                AMOUNT PAID - $null
                BALANCE - 0
                CHECK IN DATE - 2023-06-18
                CHECK OUT DATE - 2023-06-28
                DURATION OF STAY - 10 days
                AUTHORIZED BY - Brock Lesnar""",generateReceipt.toString());
        //System.out.println(generateReceipt);
    }
    @Test
    void generateReceiptByEmail(){
        Admin admin = Map.adminResponseToAdmin(superAdmin);
        ReservationRequest request = new ReservationRequest();
        request.setEmail("mikej@gmail.com");
        request.setRoomNumberChosen(2);

        ReceiptResponse secondReceipt = new ReceiptResponse();
        try {
            secondReceipt = receiptService.createReceipt(request,admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Receipt Created",secondReceipt.getMessage());

        ReceiptResponse generateReceipt = receiptService.generateReceiptByEmail(secondReceipt.getEmail());

        assertEquals("""
                \nNAME - Michael Jordan
                ROOM NUMBER - 2
                ROOM TYPE - DOUBLE
                AMOUNT PAID - $null
                BALANCE - 0
                CHECK IN DATE - 2023-06-15
                CHECK OUT DATE - 2023-06-20
                DURATION OF STAY - 5 days
                AUTHORIZED BY - Brock Lesnar""",generateReceipt.toString());

//        System.out.println(generateReceipt);
    }
    @Test
    void findAllReceipts(){
        Admin admin = Map.adminResponseToAdmin(superAdmin);
        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);
        request.setEmail("curry@gmail.com");

        ReceiptResponse firstReceipt = new ReceiptResponse();
        try {
            firstReceipt = receiptService.createReceipt(request,admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Receipt Created",firstReceipt.getMessage());

        request.setEmail("mikej@gmail.com");
        request.setRoomNumberChosen(2);

        ReceiptResponse secondReceipt = new ReceiptResponse();
        try {
            secondReceipt = receiptService.createReceipt(request,admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Receipt Created",secondReceipt.getMessage());

        List<Receipt> allReceipts = receiptService.findAllReceipts();
        assertEquals(2,allReceipts.size());
    }
    @Test
    void deleteReceiptById(){

        Admin admin = Map.adminResponseToAdmin(superAdmin);
        ReservationRequest request = new ReservationRequest();
        request.setRoomNumberChosen(1);
        request.setEmail("curry@gmail.com");

        ReceiptResponse firstReceipt = new ReceiptResponse();
        try {
            firstReceipt = receiptService.createReceipt(request,admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Receipt Created",firstReceipt.getMessage());

        request.setEmail("mikej@gmail.com");
        request.setRoomNumberChosen(2);

        ReceiptResponse secondReceipt = new ReceiptResponse();
        try {
            secondReceipt = receiptService.createReceipt(request,admin);
        } catch (AdminException ex){
            System.err.println(ex.getMessage());
        }
        assertEquals("Receipt Created",secondReceipt.getMessage());

        DeleteResponse deletedReceipt = receiptService.deleteReceiptById(secondReceipt.getId());
        assertEquals("Receipt Deleted Successfully",deletedReceipt.getMessage());
    }







    private ReservationRequest first(){
        ReservationRequest request = new ReservationRequest();

        request.setFirstName("Steph");
        request.setLastName("Curry");
        request.setEmail("curry@gmail.com");
        request.setRoomNumberChosen(1);
        request.setRoomType(SINGLE);
        request.setCheckInDate("18/06/2023");
        request.setCheckOutDate("28/06/2023");
        request.setMakePayment(BigDecimal.valueOf(500));

        return request;
    }
    private ReservationRequest second(){
        ReservationRequest request = new ReservationRequest();

        request.setFirstName("Michael");
        request.setLastName("Jordan");
        request.setEmail("mikej@gmail.com");
        request.setRoomNumberChosen(2);
        request.setRoomType(DOUBLE);
        request.setCheckInDate("15/06/2023");
        request.setCheckOutDate("20/06/2023");
        request.setMakePayment(BigDecimal.valueOf(100));

        return request;
    }
    private RegisterAdminRequest superAdmin(){
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setFirstName("Brock");
        request.setLastName("Lesnar");
        request.setPassword("7036");
        request.setEmail("brock@gmail.com");

        return request;
    }
    private RegisterAdminRequest auxiliaryAdmin(){
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setFirstName("Tiger");
        request.setLastName("Woods");
        request.setPassword("2431");
        request.setEmail("twoods@gmail.com");

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
    private RequestToCreateRoom firstRoomCreated(){
        RequestToCreateRoom request = new RequestToCreateRoom();

        request.setRoomNumber(1);
        request.setRoomType(SINGLE);
        request.setPrice(BigDecimal.valueOf(50));
        request.setAvailable(true);

        return request;
    }
    private RegisterUserRequest firstCustomer(){
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Steph");
        request.setLastName("Curry");
        request.setEmail("curry@gmail.com");
        request.setPassword("1234");

        return request;
    }
    private RegisterUserRequest secondCustomer(){
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Michael");
        request.setLastName("Jordan");
        request.setEmail("mikej@gmail.com");
        request.setPassword("5678");

        return request;
    }

}