package odogwuHotels.data.repositories;

import odogwuHotels.data.models.Reservation;
import odogwuHotels.dto.requests.UpdateReservationRequest;

import java.util.List;

public interface ReservationRepository {
    Reservation saveReservation (Reservation reservation);
    Reservation updateReservation (UpdateReservationRequest request);
    Reservation findReservationByRoomNumber(int roomNumber);
    Reservation findReservationById(int id);
    List<Reservation> findAllReservations();
    void deleteReservationByRoomNumber(int roomNumber);
    void deleteReservationById(int id);
}
