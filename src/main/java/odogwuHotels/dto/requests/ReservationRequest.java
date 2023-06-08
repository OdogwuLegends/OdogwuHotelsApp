package odogwuHotels.dto.requests;

import lombok.Data;
import odogwuHotels.data.models.FindRoomByChoice;
import odogwuHotels.data.models.RoomType;

@Data
public class ReservationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String checkInDate;
    private String checkOutDate;
    private int roomNumberChosen;
    private RoomType roomType;
    private FindRoomByChoice findRoomByChoice;
}
