package odogwuHotels.dto.responses;

import lombok.Data;
import odogwuHotels.data.models.RoomType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReceiptResponse {
    private String firstName;
    private String lastName;
    private String email;
    private int roomNumber;
    private RoomType roomType;
    private BigDecimal roomPrice;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String durationOfStay;
    private BigDecimal amountPaid;
    private BigDecimal balance;
    private String approvedBy;
    private boolean isFullyPaidFor;
    private boolean isApproved;
    private String message;

    @Override
    public String toString(){
       final StringBuffer sb = new StringBuffer();

        sb.append("\nNAME - ").append(firstName).append(" ").append(lastName);
        sb.append("\nROOM NUMBER - ").append(roomNumber);
        sb.append("\nROOM TYPE - ").append(roomType);
        sb.append("\nAMOUNT PAID - $").append(amountPaid);
        sb.append("\nBALANCE - ").append(balance);
        sb.append("\nCHECK IN DATE - ").append(checkInDate);
        sb.append("\nCHECK OUT DATE - ").append(checkOutDate);
        sb.append("\nDURATION OF STAY - ").append(durationOfStay).append(" days");
        sb.append("\nAUTHORIZED BY - ").append(approvedBy);

        return sb.toString();
    }
}
