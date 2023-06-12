package odogwuHotels.services;

import odogwuHotels.data.models.Reservation;
import odogwuHotels.dto.requests.ReceiptRequest;
import odogwuHotels.dto.requests.ReservationRequest;
import odogwuHotels.dto.requests.UpdateReservationRequest;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.ReservationResponse;
import odogwuHotels.dto.responses.UpdateResponse;

import java.util.List;

public interface ReservationService {
    ReservationResponse makeReservation(ReservationRequest request);
    ReservationResponse findReservationById(int id);
    ReservationResponse findReservationByRoomNumber(ReservationRequest request);
    UpdateResponse updateReservation (UpdateReservationRequest request);
    ReservationResponse checkIn(ReceiptRequest request);
    ReservationResponse checkOut(ReceiptRequest request);
    List<Reservation> findAllReservations();
    DeleteResponse deleteReservationByRoomNumber(ReservationRequest request);
    DeleteResponse deleteReservationById(int id);

}
