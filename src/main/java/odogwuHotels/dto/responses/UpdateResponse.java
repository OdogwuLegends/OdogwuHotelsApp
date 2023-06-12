package odogwuHotels.dto.responses;

import lombok.Data;
import odogwuHotels.data.models.RoomType;

import java.math.BigDecimal;

@Data
public class UpdateResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private RoomType roomType;
    private int roomNumberChosen;
    private BigDecimal roomPrice;
    private boolean isAvailable;
    private String checkInDate;
    private String checkOutDate;
    private int id;
    private String message;
}
