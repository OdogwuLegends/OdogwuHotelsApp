package odogwuHotels.dto.requests;

import lombok.Data;
import odogwuHotels.data.models.FindRoomByType;
import odogwuHotels.data.models.RoomType;

import java.math.BigDecimal;

@Data
public class ReservationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private RoomType roomType;
    private int roomNumberChosen;
    private String checkInDate;
    private String checkOutDate;
    private BigDecimal makePayment;
    private FindRoomByType findRoomByType;
}
