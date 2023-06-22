package odogwuHotels.myUtils;

import odogwuHotels.data.models.*;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Map {
    public static SearchResponse roomToSearchResponse(Room room){
        SearchResponse response = new SearchResponse();

        response.setRoomId(room.getId());
        response.setRoomNumber(room.getRoomNumber());
        response.setRoomType(room.getRoomType());
        response.setPrice(room.getPrice());
        response.setAvailable(room.isAvailable());

        return response;
    }

    public static RoomCreationResponse roomToCreationResponse(Room room){
        RoomCreationResponse creationResponse = new RoomCreationResponse();

        creationResponse.setRoomNumber(room.getRoomNumber());
        creationResponse.setRoomId(room.getId());
        creationResponse.setPrice(room.getPrice());
        creationResponse.setRoomType(room.getRoomType());

        return creationResponse;
    }
    public static UpdateResponse roomToUpdateResponse(Room room){
        UpdateResponse response = new UpdateResponse();

        response.setAvailable(room.isAvailable());
        response.setRoomPrice(room.getPrice());
        response.setRoomNumberChosen(room.getRoomNumber());
        response.setRoomType(room.getRoomType());
        response.setId(room.getId());

        return response;
    }
    public static DeleteResponse roomToDeleteResponse(Room room){
        DeleteResponse response = new DeleteResponse();

        response.setRoomNumber(room.getRoomNumber());
        return response;
    }

    public static StringBuilder listToBuilder(List<Integer> numbers){
        StringBuilder builder = new StringBuilder();
        if(numbers.isEmpty()){
            builder.append("=> [Sorry, no room found.]");
        } else{
            for (Integer number : numbers) {
                builder.append(number).append(", ");
            }
            int lastIndex = builder.length()-1;
        builder.setCharAt(lastIndex-1,' ');
        }

        return builder;
    }

    public static Customer customerRequestToCustomer(RegisterUserRequest request){
        Customer customer = new Customer();

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());

        return customer;
    }
    public static RegisterUserResponse customerToRegUserResponse(Customer customer){
        RegisterUserResponse response = new RegisterUserResponse();

        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setId(customer.getId());
        response.setPassword(customer.getPassword());

        return response;
    }
    public static UpdateResponse customerToUpdateResponse(Customer customer){
        UpdateResponse response = new UpdateResponse();

        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setId(customer.getId());
        response.setPassword(customer.getPassword());

        return response;
    }
    public static DeleteResponse customerToDeleteResponse(Customer customer){
        DeleteResponse response = new DeleteResponse();

        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setId(customer.getId());
        response.setPassword(customer.getPassword());

        return response;
    }
    public static Admin adminRequestToAdmin(RegisterUserRequest request){
        Admin admin = new Admin();

        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());

        return admin;
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
        room.setAvailable(response.isAvailable());

        Reservation reservation = new Reservation();
        reservation.setCheckInDate(response.getCheckInDate());
        reservation.setCheckOutDate(response.getCheckOutDate());
        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setId(response.getId());

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
        response.setAmountPaid((BigDecimal) receipt.getAmountPaid());
        response.setBalance(reservation.getRoom().getPrice().subtract(response.getAmountPaid()));

        return response;
    }
    public static Receipt createReceipt(Reservation reservation, Admin admin){
        Receipt receipt = new Receipt();

        receipt.setFirstName(reservation.getCustomer().getFirstName());
        receipt.setLastName(reservation.getCustomer().getLastName());
        receipt.setEmail(reservation.getCustomer().getEmail());
        receipt.setRoomNumber(reservation.getRoom().getRoomNumber());
        receipt.setRoomPrice(reservation.getRoom().getPrice());
        receipt.setRoomType(reservation.getRoom().getRoomType());
        receipt.setCheckInDate(reservation.getCheckInDate());
        receipt.setCheckOutDate(reservation.getCheckOutDate());

        long duration = ChronoUnit.DAYS.between(reservation.getCheckOutDate(),reservation.getCheckInDate());
        receipt.setDurationOfStay(Utils.durationOfStay(duration));
        receipt.setApprovedBy(admin.getFirstName()+" "+admin.getLastName());

        return receipt;
    }
    public static ReceiptResponse receiptToResponse(Receipt receipt){
        ReceiptResponse response = new ReceiptResponse();

        response.setFirstName(receipt.getFirstName());
        response.setLastName(receipt.getLastName());
        response.setEmail(receipt.getEmail());
        response.setRoomNumber(receipt.getRoomNumber());
        response.setRoomType(receipt.getRoomType());
        response.setRoomPrice(receipt.getRoomPrice());
        response.setCheckInDate(receipt.getCheckInDate());
        response.setCheckOutDate(receipt.getCheckOutDate());
        response.setDurationOfStay(receipt.getDurationOfStay());
        response.setAmountPaid(receipt.getAmountPaid());
        response.setBalance(receipt.getBalance());
        response.setApprovedBy(receipt.getApprovedBy());
        response.setApproved(receipt.isApproved());
        response.setFullyPaidFor(receipt.isFullyPaidFor());
        response.setId(receipt.getId());

        return response;
    }
    public static Receipt responseToReceipt(ReceiptResponse response){
        Receipt receipt = new Receipt();

        receipt.setFirstName(response.getFirstName());
        receipt.setLastName(response.getLastName());
        receipt.setEmail(response.getEmail());
        receipt.setRoomNumber(response.getRoomNumber());
        receipt.setRoomType(response.getRoomType());
        receipt.setRoomPrice(response.getRoomPrice());
        receipt.setCheckInDate(response.getCheckInDate());
        receipt.setCheckOutDate(response.getCheckOutDate());
        receipt.setDurationOfStay(response.getDurationOfStay());
        receipt.setAmountPaid(response.getAmountPaid());
        receipt.setBalance(response.getBalance());
        receipt.setApprovedBy(response.getApprovedBy());
        receipt.setApproved(response.isApproved());
        receipt.setFullyPaidFor(response.isFullyPaidFor());

        return receipt;
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
    public static UpdateResponse updateReservationToResponse(Reservation updatedReservation){
        UpdateResponse response = new UpdateResponse();

        response.setFirstName(updatedReservation.getCustomer().getFirstName());
        response.setLastName(updatedReservation.getCustomer().getLastName());
        response.setEmail(updatedReservation.getCustomer().getEmail());
        response.setRoomType(updatedReservation.getRoom().getRoomType());
        response.setRoomNumberChosen(updatedReservation.getRoom().getRoomNumber());
        response.setRoomPrice(updatedReservation.getRoom().getPrice());
        response.setAvailable(updatedReservation.getRoom().isAvailable());
        response.setCheckInDate(Utils.localDateToString(updatedReservation.getCheckInDate()));
        response.setCheckOutDate(Utils.localDateToString(updatedReservation.getCheckOutDate()));
        response.setId(updatedReservation.getId());

        return response;
    }
    public static Reservation updateReservationReqToReservation(UpdateReservationRequest request){
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());

        Room room = new Room();
        room.setRoomType(request.getRoomType());
        room.setRoomNumber(request.getRoomNumberChosen());
        room.setAvailable(request.isAvailable());

        Reservation reservation = new Reservation();
        reservation.setCheckInDate(Utils.stringToLocalDate(request.getCheckInDate()));
        reservation.setCheckOutDate(Utils.stringToLocalDate(request.getCheckOutDate()));
        reservation.setRoom(room);
        reservation.setCustomer(customer);
        reservation.setId(request.getId());

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
        response.setRoomPrice(reservation.getRoom().getPrice());
        response.setRoomNumber(reservation.getRoom().getRoomNumber());
        response.setAvailable(reservation.getRoom().isAvailable());
        response.setId(reservation.getId());

        return response;
    }
    public static Reservation updatedReservationResToReservation(UpdateResponse response){
        Reservation reservation = new Reservation();

        Customer customer = new Customer();
        customer.setFirstName(response.getFirstName());
        customer.setLastName(response.getLastName());
        customer.setEmail(response.getEmail());

        Room room = new Room();
        room.setAvailable(response.isAvailable());
        room.setPrice(response.getRoomPrice());
        room.setRoomType(response.getRoomType());
        room.setRoomNumber(response.getRoomNumberChosen());

        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setId(response.getId());
        reservation.setCheckInDate(Utils.stringToLocalDate(response.getCheckInDate()));
        reservation.setCheckOutDate(Utils.stringToLocalDate(response.getCheckOutDate()));

        return reservation;
    }
    public static Reservation reservationResToReservation(ReservationResponse response){
        Customer customer = new Customer();
        customer.setFirstName(response.getFirstName());
        customer.setLastName(response.getLastName());
        customer.setEmail(response.getEmail());

        Room room = new Room();
        room.setRoomType(response.getRoomType());
        room.setRoomNumber(response.getRoomNumber());
        room.setPrice(response.getRoomPrice());

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setCheckInDate(response.getCheckInDate());
        reservation.setCheckOutDate(response.getCheckOutDate());

        return reservation;
    }
    public static RoomSearchRequest reservationReqToRoomSearchResponse(ReservationRequest request){
        RoomSearchRequest roomSearchRequest = new RoomSearchRequest();
        roomSearchRequest.setRoomNumberChosen(request.getRoomNumberChosen());

        return roomSearchRequest;
    }
    public static RoomSearchRequest updateReservationRequestToRoomSearchResponse(UpdateReservationRequest request){
        RoomSearchRequest response = new RoomSearchRequest();
        response.setRoomNumberChosen(request.getNewRoomNumberChosen());
        return response;
    }
    public static Room roomSearchResponseToRoom(SearchResponse response){
        Room room = new Room();

        room.setRoomNumber(response.getRoomNumber());
        room.setRoomType(response.getRoomType());
        room.setId(response.getRoomId());
        room.setPrice(response.getPrice());
        room.setAvailable(response.isAvailable());

        return room;
    }
    public static UserResponse customerToCustomerResponse(Customer customer){
        UserResponse response = new UserResponse();
        response.setId(customer.getId());
        response.setPassword(customer.getPassword());
        response.setEmail(customer.getEmail());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());

        return response;
    }
    public static AdminResponse adminToAdminResponse(Admin admin){
        AdminResponse response = new AdminResponse();

        response.setFirstName(admin.getFirstName());
        response.setLastName(admin.getLastName());
        response.setEmail(admin.getEmail());
        response.setId(admin.getId());
        response.setPassword(admin.getPassword());
        response.setAdminCode(admin.getAdminCode());
        response.setSuperAdmin(admin.isSuperAdmin());
        response.setApproveNewAdmin(admin.isApproveNewAdmin());

        return response;
    }
    public static Admin adminResponseToAdmin(AdminResponse response){
        Admin admin = new Admin();

        admin.setFirstName(response.getFirstName());
        admin.setLastName(response.getLastName());
        admin.setEmail(response.getEmail());
        admin.setPassword(response.getPassword());
        admin.setSuperAdmin(response.isSuperAdmin());
        admin.setAdminCode(response.getAdminCode());
        admin.setApproveNewAdmin(response.isApproveNewAdmin());
        admin.setId(response.getId());

        return admin;
    }
    public static UpdateResponse adminToUpdateResponse(Admin admin){
        UpdateResponse response = new UpdateResponse();

        response.setFirstName(admin.getFirstName());
        response.setLastName(admin.getLastName());
        response.setEmail(admin.getEmail());
        response.setPassword(admin.getPassword());
        response.setSuperAdmin(admin.isSuperAdmin());
        response.setId(admin.getId());
        response.setAdminCode(admin.getAdminCode());

        return response;
    }

    public static DeleteResponse adminToDeleteResponse(Admin admin){
        DeleteResponse response = new DeleteResponse();

        response.setId(admin.getId());
        response.setFirstName(admin.getFirstName());
        response.setLastName(admin.getLastName());
        response.setEmail(admin.getEmail());
        response.setPassword(admin.getPassword());

        return response;
    }
    public static Customer customerResponseToCustomer(UserResponse response){
        Customer customer = new Customer();
        customer.setFirstName(response.getFirstName());
        customer.setLastName(response.getLastName());
        customer.setEmail(response.getEmail());
        customer.setPassword(response.getPassword());
        customer.setId(response.getId());

        return customer;
    }
    public static FeedBackResponse feedBackToResponse(FeedBack feedBack){
        FeedBackResponse response = new FeedBackResponse();

        response.setMessage(feedBack.getMessage());
        response.setId(feedBack.getId());

        return response;
    }

}
