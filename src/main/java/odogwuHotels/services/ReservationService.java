package odogwuHotels.services;

import odogwuHotels.data.models.Reservation;
import odogwuHotels.dto.requests.ReceiptRequest;
import odogwuHotels.dto.requests.ReservationRequest;
import odogwuHotels.dto.requests.UpdateReservationRequest;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.ReservationResponse;
import odogwuHotels.dto.responses.UpdateResponse;
import odogwuHotels.exceptions.EntityNotFoundException;

import java.util.List;

public interface ReservationService {
    ReservationResponse makeReservation(ReservationRequest request);
    ReservationResponse findReservationById(int id) throws EntityNotFoundException;
    ReservationResponse findReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException;
    UpdateResponse updateReservation (UpdateReservationRequest request);
    ReservationResponse checkIn(ReceiptRequest request) throws EntityNotFoundException;
    ReservationResponse checkOut(ReceiptRequest request);
    List<Reservation> findAllReservations();
    DeleteResponse deleteReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException;
    DeleteResponse deleteReservationById(int id) throws EntityNotFoundException;
    DeleteResponse deleteAll();


}
