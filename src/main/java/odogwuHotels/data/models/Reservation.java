package odogwuHotels.data.models;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Reservation {
    private Room room;
    private Customer customer; //ADDED
    private Receipt paymentReceipt; //TO DO
    private boolean isBooked;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int id;
}
