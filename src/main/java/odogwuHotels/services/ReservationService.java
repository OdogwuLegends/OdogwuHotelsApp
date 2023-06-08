package odogwuHotels.services;

import odogwuHotels.data.models.Reservation;
import odogwuHotels.dto.requests.ReservationRequest;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.ReservationResponse;

import java.util.List;

public interface ReservationService {
    ReservationResponse makeReservation(ReservationRequest request);
    ReservationResponse findReservationById(ReservationRequest request);
    ReservationResponse findReservationByRoomNumber(ReservationRequest request);
    String checkIn(ReservationRequest request);
    String checkOut(ReservationRequest request);
    List<Reservation> findAllReservations();
    DeleteResponse deleteReservationByRoomNumber(ReservationRequest request);
    DeleteResponse deleteReservationById(ReservationRequest request);

}
