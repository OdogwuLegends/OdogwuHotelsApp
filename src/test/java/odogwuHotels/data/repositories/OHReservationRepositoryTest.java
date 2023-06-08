package odogwuHotels.data.repositories;

import odogwuHotels.Utils;
import odogwuHotels.data.models.Customer;
import odogwuHotels.data.models.Receipt;
import odogwuHotels.data.models.Reservation;
import odogwuHotels.data.models.Room;
import odogwuHotels.dto.requests.UpdateReservationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

class OHReservationRepositoryTest {
    private final ReservationRepository reservationRepository = new OHReservationRepository();

    private Reservation firstReservationSaved;
    private Reservation secondReservationSaved;

    @BeforeEach
    void setUp(){
        firstReservationSaved = reservationRepository.saveReservation(firstReservation());
        secondReservationSaved = reservationRepository.saveReservation(secondReservation());
    }

    @Test
    void SaveReservation(){
        assertNotNull(firstReservationSaved);
        assertNotNull(secondReservationSaved);

        List<Reservation> allReservations = reservationRepository.findAllReservations();
        assertEquals(2,allReservations.size());
        assertTrue(allReservations.contains(secondReservationSaved));
    }
    @Test
    void updateReservation(){
        assertEquals(4,firstReservationSaved.getRoom().getRoomNumber());
        assertEquals(SINGLE,firstReservationSaved.getRoom().getRoomType());

        UpdateReservationRequest request = new UpdateReservationRequest();
        request.setRoomNumberChosen(4);
        request.setNewRoomNumberChosen(10);
        request.setRoomType(DOUBLE);
        request.setCheckInDate("25/06/2023");
        request.setCheckOutDate("30/06/2023");

        Reservation updatedReservation = reservationRepository.updateReservation(request);

        assertEquals(10,firstReservationSaved.getRoom().getRoomNumber());
        assertEquals(DOUBLE,firstReservationSaved.getRoom().getRoomType());
        assertSame(firstReservationSaved,updatedReservation);
        assertEquals(firstReservationSaved.getCheckOutDate(),updatedReservation.getCheckOutDate());
        assertEquals(firstReservationSaved.isBooked(),updatedReservation.isBooked());
    }
    @Test
    void findReservationByRoomNumber(){
        Reservation foundReservation = reservationRepository.findReservationByRoomNumber(firstReservationSaved.getRoom().getRoomNumber());
        assertSame(firstReservationSaved,foundReservation);
        assertEquals(4,foundReservation.getRoom().getRoomNumber());
        System.out.println(firstReservationSaved.getId());
        System.out.println(foundReservation.getId());
    }
    @Test
    void findReservationById(){
        Reservation foundReservation = reservationRepository.findReservationById(secondReservationSaved.getId());
        assertSame(secondReservationSaved,foundReservation);
        assertTrue(foundReservation.isBooked());
    }
    @Test
    void findAllReservations(){
        List<Reservation> allReservations = reservationRepository.findAllReservations();
        assertEquals(2,allReservations.size());
        assertTrue(allReservations.contains(firstReservationSaved));
        assertTrue(allReservations.contains(secondReservationSaved));
    }
    @Test
    void deleteReservationByRoomNumber(){
        List<Reservation> allReservations = reservationRepository.findAllReservations();
        assertEquals(2,allReservations.size());

        reservationRepository.deleteReservationByRoomNumber(secondReservationSaved.getRoom().getRoomNumber());
        assertEquals(1,allReservations.size());


        reservationRepository.deleteReservationByRoomNumber(firstReservationSaved.getRoom().getRoomNumber());
        assertEquals(0,allReservations.size());
    }
    @Test
    void deleteReservationById(){
        List<Reservation> allReservations = reservationRepository.findAllReservations();
        assertEquals(2,allReservations.size());

        reservationRepository.deleteReservationById(firstReservationSaved.getId());
        assertEquals(1,allReservations.size());

        reservationRepository.deleteReservationById(secondReservationSaved.getId());
        assertEquals(0,allReservations.size());
    }

    private Reservation firstReservation(){
        Reservation reservation = new Reservation();
        reservation.setId(Utils.generateId());

        Customer customer = new Customer();
        customer.setFirstName("Legend");
        customer.setLastName("Boss");
        reservation.setCustomer(customer);

        Room room = new Room();
        room.setRoomType(SINGLE);
        room.setRoomNumber(4);
        room.setPrice(BigDecimal.valueOf(50));
        room.setAvailable(false);
        room.setId(Utils.generateId());
        reservation.setRoom(room);

        reservation.setCheckInDate(Utils.stringToLocalDate("06/06/2023"));
        reservation.setCheckOutDate(Utils.stringToLocalDate("11/06/2023"));

        reservation.setBooked(true);

        Receipt receipt = new Receipt();
        receipt.setAmountPaid(BigDecimal.valueOf(50));
        reservation.setPaymentReceipt(receipt);

        return reservation;
    }

    private Reservation secondReservation(){
        Reservation reservation = new Reservation();
        reservation.setId(Utils.generateId());

        Customer customer = new Customer();
        customer.setFirstName("Ned");
        customer.setLastName("Stark");
        reservation.setCustomer(customer);

        Room room = new Room();
        room.setRoomType(DOUBLE);
        room.setRoomNumber(5);
        room.setPrice(BigDecimal.valueOf(100));
        room.setAvailable(false);
        room.setId(Utils.generateId());
        reservation.setRoom(room);

        reservation.setCheckInDate(Utils.stringToLocalDate("16/06/2023"));
        reservation.setCheckOutDate(Utils.stringToLocalDate("19/06/2023"));

        reservation.setBooked(true);

        Receipt receipt = new Receipt();
        receipt.setAmountPaid(BigDecimal.valueOf(50));
        reservation.setPaymentReceipt(receipt);

        return reservation;
    }


}