package odogwuHotels.data.repositories;

import odogwuHotels.myUtils.Utils;
import odogwuHotels.data.models.Customer;
import odogwuHotels.data.models.Reservation;
import odogwuHotels.data.models.Room;
import odogwuHotels.dto.requests.UpdateReservationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

class OHReservationRepositoryTest {
    private final ReservationRepository reservationRepository = OHReservationRepository.createObject();
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

        int index = reservationRepository.getIndex(firstReservationSaved);

        UpdateReservationRequest request = new UpdateReservationRequest();
        request.setRoomNumberChosen(4);
//        request.setNewRoomNumberChosen(10);
        request.setCheckInDate("25/06/2023");
        request.setCheckOutDate("30/06/2023");
        request.setLastName("Odogwu");

        firstReservationSaved.setCheckInDate(Utils.stringToLocalDate(request.getCheckInDate()));
        firstReservationSaved.setCheckOutDate(Utils.stringToLocalDate(request.getCheckOutDate()));
        firstReservationSaved.getCustomer().setLastName(request.getLastName());

        Reservation updatedReservation = reservationRepository.updateReservation(index,firstReservationSaved);

        assertEquals(firstReservationSaved.getCustomer().getLastName(),updatedReservation.getCustomer().getLastName());
        assertEquals(firstReservationSaved.getCheckInDate(),updatedReservation.getCheckInDate());
        assertSame(firstReservationSaved,updatedReservation);
        assertEquals(firstReservationSaved.getCheckOutDate(),updatedReservation.getCheckOutDate());
    }
//    @Test
//    @DisplayName("Test that room features change when room number is updated in reservation.")
//    void testToChangeTheRoomFeatures(){
//        assertEquals(4,firstReservationSaved.getRoom().getRoomNumber());
//        assertEquals(SINGLE,firstReservationSaved.getRoom().getRoomType());
//
//        int index = reservationRepository.getIndex(firstReservationSaved);
//
//        UpdateReservationRequest request = new UpdateReservationRequest();
//        request.setRoomNumberChosen(4);
//        request.setNewRoomNumberChosen(5);
//
//        firstReservationSaved.getRoom().setRoomNumber(request.getNewRoomNumberChosen());
//        Reservation updatedReservation = reservationRepository.updateReservation(index,firstReservationSaved);
//
////        assertEquals(DOUBLE,firstReservationSaved.getRoom().getRoomType());
//        assertEquals(DOUBLE,updatedReservation.getRoom().getRoomType());
//
//    }
    @Test
    void findReservationByRoomNumber(){
        Reservation foundReservation = reservationRepository.findReservationByRoomNumber(firstReservationSaved.getRoom().getRoomNumber());
        assertSame(firstReservationSaved,foundReservation);
        assertEquals(4,foundReservation.getRoom().getRoomNumber());
    }
    @Test
    void findReservationById(){
        Reservation foundReservation = reservationRepository.findReservationById(secondReservationSaved.getId());
        assertSame(secondReservationSaved,foundReservation);
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

        Customer customer = new Customer();
        customer.setFirstName("Legend");
        customer.setLastName("Boss");
        customer.setEmail("Odogwu@gmail.com");
        reservation.setCustomer(customer);

        Room room = new Room();
        room.setRoomType(SINGLE);
        room.setRoomNumber(4);
        room.setPrice(BigDecimal.valueOf(50));
        room.setAvailable(false);
        reservation.setRoom(room);

        reservation.setCheckInDate(Utils.stringToLocalDate("06/06/2023"));
        reservation.setCheckOutDate(Utils.stringToLocalDate("11/06/2023"));



        return reservation;
    }

    private Reservation secondReservation(){
        Reservation reservation = new Reservation();

        Customer customer = new Customer();
        customer.setFirstName("Ned");
        customer.setLastName("Stark");
        customer.setEmail("stark@gmail.com");
        reservation.setCustomer(customer);

        Room room = new Room();
        room.setRoomType(DOUBLE);
        room.setRoomNumber(5);
        room.setPrice(BigDecimal.valueOf(100));
        room.setAvailable(false);
        reservation.setRoom(room);

        reservation.setCheckInDate(Utils.stringToLocalDate("16/06/2023"));
        reservation.setCheckOutDate(Utils.stringToLocalDate("19/06/2023"));


        return reservation;
    }

    @AfterEach
    void cleanUp(){
        reservationRepository.deleteReservationByRoomNumber(4);
        reservationRepository.deleteReservationByRoomNumber(5);
    }

}