package odogwuHotels.data.models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Receipt {
    private String firstName;
    private String lastName;
    private RoomType roomType;
    private BigDecimal roomPrice;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String durationOfStay;
    private BigDecimal amountPaid;
    private BigDecimal balance;
    private boolean isFullyPaidFor;
    private String approvedBy;
    private int id;
}
