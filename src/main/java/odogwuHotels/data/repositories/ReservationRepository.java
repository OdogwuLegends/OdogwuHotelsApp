package odogwuHotels.data.repositories;

import odogwuHotels.data.models.Reservation;
import odogwuHotels.dto.requests.UpdateReservationRequest;

import java.util.List;
import java.util.Map;

public interface ReservationRepository {
    Reservation saveReservation (Reservation reservation);
    Reservation updateReservation (int index, Reservation reservationToUpdate);
    Reservation findReservationByRoomNumber(int roomNumber);
    int getIndex(Reservation reservationToCheck);
    Map<Integer, Reservation> getIdsOfAllReservations();
    Reservation findReservationById(int id);
    List<Reservation> findAllReservations();
    void deleteReservationByRoomNumber(int roomNumber);
    void deleteReservationById(int id);
    void deleteAll();
}
