package odogwuHotels.dto.responses;

import lombok.Data;
import odogwuHotels.data.models.RoomType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReservationResponse {
    private String firstName;
    private String lastName;
    private String email;
    private int roomNumber;
    private BigDecimal roomPrice;
    private RoomType roomType;
    private boolean isAvailable;
    private int id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String message;

    public String toString(){
        final StringBuffer sb = new StringBuffer();

        sb.append("\nNAME - ").append(firstName).append(" ").append(lastName);
        sb.append("\nROOM NUMBER - ").append(roomNumber);
        sb.append("\nROOM TYPE - ").append(roomType);
        sb.append("\nCHECK IN DATE - ").append(checkInDate);
        sb.append("\nCHECK OUT DATE - ").append(checkOutDate);

        return sb.toString();
    }
}
