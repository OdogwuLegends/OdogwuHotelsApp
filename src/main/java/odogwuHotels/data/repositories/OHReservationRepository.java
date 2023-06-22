package odogwuHotels.data.repositories;

import odogwuHotels.myUtils.Utils;
import odogwuHotels.data.models.Reservation;

import java.util.*;

public class OHReservationRepository implements ReservationRepository{
    static List<Reservation> reservationList = new ArrayList<>();

    private static OHReservationRepository singleObject;

    private OHReservationRepository(){

    }
    public static OHReservationRepository createObject(){
        if(singleObject == null){
            singleObject = new OHReservationRepository();
        }
        return singleObject;
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
        reservation.setId(Utils.generateId());
        reservationList.add(reservation);
        return reservation;
    }

    @Override
    public Reservation updateReservation(int index, Reservation reservationToUpdate) {
        reservationList.set(index,reservationToUpdate);
        return reservationToUpdate;
    }

    public Reservation findReservationByRoomNumber(int roomNumber) {
        for(Reservation foundReservation : reservationList){
            if(Objects.equals(foundReservation.getRoom().getRoomNumber(),roomNumber))
                return foundReservation;
        }
        return null;
    }
    @Override
    public int getIndex(Reservation reservationToCheck){
        int index = 0;
        for (Reservation reservation : reservationList){
            if(Objects.equals(reservation,reservationToCheck))
                index = reservationList.indexOf(reservation);
        }
        return index;
    }
    @Override
    public Map<Integer, Reservation> getIdsOfAllReservations(){
        Map<Integer, Reservation> reservationsId = new TreeMap<>();
        for(Reservation reservation : reservationList){
            reservationsId.put(reservation.getId(), reservation);
        }
        return reservationsId;
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

    @Override
    public void deleteAll() {
        reservationList.clear();
    }

}
