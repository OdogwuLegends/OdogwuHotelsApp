package odogwuHotels;

import odogwuHotels.data.models.*;
import odogwuHotels.dto.requests.ReceiptRequest;
import odogwuHotels.dto.requests.RegisterCustomerRequest;
import odogwuHotels.dto.requests.ReservationRequest;
import odogwuHotels.dto.requests.RoomSearchRequest;
import odogwuHotels.dto.responses.*;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class Map {
    public static RoomSearchResponse roomToResponse(Room room){
        RoomSearchResponse response = new RoomSearchResponse();

        response.setRoomId(room.getId());
        response.setRoomNumber(room.getRoomNumber());
        response.setRoomType(room.getRoomType());
        response.setPrice(room.getPrice());

        return response;
    }

    public static RoomCreationResponse creationResponse(RoomCreationResponse response){
        RoomCreationResponse creationResponse = new RoomCreationResponse();

        creationResponse.setRoomNumber(response.getRoomNumber());
        creationResponse.setRoomId(response.getRoomId());
        creationResponse.setPrice(response.getPrice());
        creationResponse.setRoomType(response.getRoomType());
        creationResponse.setMessage(response.getMessage());

        return creationResponse;
    }

    public static StringBuilder listToBuilder(List<Integer> numbers){
        StringBuilder builder = new StringBuilder();

        for (Integer number : numbers) {
            builder.append(number).append(", ");
        }
        int lastIndex = builder.length()-1;
        builder.setCharAt(lastIndex-1,' ');
        return builder;
    }

    public static Customer customerRequestToCustomer(RegisterCustomerRequest request){
        Customer customer = new Customer();

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());

        return customer;
    }
    public static Reservation reservationResponseToReservation(ReservationResponse response){
        Customer customer = new Customer();
        customer.setFirstName(response.getFirstName());
        customer.setLastName(response.getLastName());
        customer.setEmail(response.getEmail());

        Room room = new Room();
        room.setRoomNumber(response.getRoomNumber());
        room.setRoomType(response.getRoomType());
        room.setPrice(response.getRoomPrice());

        Reservation reservation = new Reservation();
        reservation.setCheckInDate(response.getCheckInDate());
        reservation.setCheckOutDate(response.getCheckOutDate());
        reservation.setCustomer(customer);
        reservation.setRoom(room);

        return reservation;
    }
    public static ReservationRequest receiptReqToReservationReq(ReceiptRequest receiptRequest){
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setRoomNumberChosen(receiptRequest.getRoomNumber());
        return reservationRequest;
    }
    public static ReceiptResponse receiptRequestToResponse(Reservation reservation, Admin admin, Receipt receipt){
        ReceiptResponse response = new ReceiptResponse();

        response.setFirstName(reservation.getCustomer().getFirstName());
        response.setLastName(reservation.getCustomer().getLastName());
        response.setApprovedBy(admin.getFirstName()+" "+admin.getLastName());
        response.setCheckInDate(reservation.getCheckInDate());
        response.setCheckOutDate(reservation.getCheckOutDate());

        long duration = ChronoUnit.DAYS.between(reservation.getCheckOutDate(),reservation.getCheckInDate());
        response.setDurationOfStay(Utils.durationOfStay(duration));
        response.setRoomType(reservation.getRoom().getRoomType());
        response.setRoomPrice(reservation.getRoom().getPrice());
        response.setRoomNumber(reservation.getRoom().getRoomNumber());
        response.setAmountPaid(receipt.getAmountPaid());
        response.setBalance(reservation.getRoom().getPrice().subtract(response.getAmountPaid()));

        return response;
    }
    public static Reservation reservationReqToReservation(ReservationRequest request,Customer foundCustomer, Room roomToReserve){
        Customer customer = new Customer();
        customer.setFirstName(foundCustomer.getFirstName());
        customer.setLastName(foundCustomer.getLastName());
        customer.setEmail(foundCustomer.getEmail());
        customer.setPassword(foundCustomer.getPassword());
        customer.setId(foundCustomer.getId());

        Room room = new Room();
        room.setRoomType(roomToReserve.getRoomType());
        room.setRoomNumber(roomToReserve.getRoomNumber());
        room.setAvailable(roomToReserve.isAvailable());
        room.setId(roomToReserve.getId());
        room.setPrice(roomToReserve.getPrice());

        Reservation reservation = new Reservation();
        reservation.setCheckInDate(Utils.stringToLocalDate(request.getCheckInDate()));
        reservation.setCheckOutDate(Utils.stringToLocalDate(request.getCheckOutDate()));
        reservation.setRoom(room);
        reservation.setCustomer(customer);

        return reservation;
    }
    public static ReservationResponse reservationToReservationResponse(Reservation reservation){
        ReservationResponse response = new ReservationResponse();

        response.setFirstName(reservation.getCustomer().getFirstName());
        response.setLastName(reservation.getCustomer().getLastName());
        response.setEmail(reservation.getCustomer().getEmail());
        response.setCheckInDate(reservation.getCheckInDate());
        response.setCheckOutDate(reservation.getCheckOutDate());
        response.setRoomType(reservation.getRoom().getRoomType());
        response.setRoomNumber(reservation.getRoom().getRoomNumber());

        return response;
    }
    public static RoomSearchRequest reservationReqToRoomSearchResponse(ReservationRequest request){
        RoomSearchRequest roomSearchRequest = new RoomSearchRequest();
        roomSearchRequest.setRoomNumberChosen(request.getRoomNumberChosen());

        return roomSearchRequest;
    }
    public static Room roomSearchResponseToRoom(RoomSearchResponse response){
        Room room = new Room();
        room.setRoomNumber(response.getRoomNumber());
        room.setRoomType(response.getRoomType());
        room.setId(response.getRoomId());
        room.setPrice(response.getPrice());
        room.setAvailable(response.isAvailable());

        return room;
    }
    public static CustomerResponse customerToCustomerResponse(Customer customer){
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setPassword(customer.getPassword());
        response.setEmail(customer.getEmail());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());

        return response;
    }
    public static Customer customerResponseToCustomer(CustomerResponse response){
        Customer customer = new Customer();
        customer.setFirstName(response.getFirstName());
        customer.setLastName(response.getLastName());
        customer.setEmail(response.getEmail());
        customer.setPassword(response.getPassword());
        customer.setId(response.getId());

        return customer;
    }

}
