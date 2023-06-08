package odogwuHotels.data.repositories;

import odogwuHotels.Utils;
import odogwuHotels.data.models.Reservation;
import odogwuHotels.dto.requests.UpdateReservationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OHReservationRepository implements ReservationRepository{
    List<Reservation> reservationList = new ArrayList<>();

    @Override
    public Reservation saveReservation(Reservation reservation) {
        reservation.setId(Utils.generateId());
        reservationList.add(reservation);
        return reservation;
    }

    @Override
    public Reservation updateReservation(UpdateReservationRequest request) {
        Reservation foundReservation = findReservationByRoomNumber(request.getRoomNumberChosen());

        if(foundReservation != null){
            if(request.getNewRoomNumberChosen() > 0) foundReservation.getRoom().setRoomNumber(request.getNewRoomNumberChosen());
            if(request.getCheckInDate() != null) foundReservation.setCheckInDate(Utils.stringToLocalDate(request.getCheckInDate()));
            if(request.getCheckOutDate() != null) foundReservation.setCheckOutDate(Utils.stringToLocalDate(request.getCheckOutDate()));
            if(request.getRoomType() != null) foundReservation.getRoom().setRoomType(request.getRoomType());
            if(request.isBooked() != foundReservation.isBooked()) foundReservation.setBooked(request.isBooked());
            return foundReservation;
        }
        return null;
    }

    public Reservation findReservationByRoomNumber(int roomNumber) {
        for(Reservation foundReservation : reservationList){
            if(Objects.equals(foundReservation.getRoom().getRoomNumber(),roomNumber))
                return foundReservation;
        }
        return null;
    }

    @Override
    public Reservation findReservationById(int id) {
        for(Reservation foundReservation : reservationList){
            if(Objects.equals(foundReservation.getId(),id))
                return foundReservation;
        }
        return null;
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationList;
    }

    @Override
    public void deleteReservationByRoomNumber(int roomNumber) {
        Reservation foundReservation = findReservationByRoomNumber(roomNumber);
        if(foundReservation != null) reservationList.remove(foundReservation);
    }

    @Override
    public void deleteReservationById(int id) {
        Reservation foundReservation = findReservationById(id);
        if(foundReservation != null) reservationList.remove(foundReservation);
    }

}
