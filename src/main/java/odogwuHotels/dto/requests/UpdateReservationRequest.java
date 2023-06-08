package odogwuHotels.dto.requests;

import lombok.Data;

@Data
public class UpdateReservationRequest extends ReservationRequest{
    private int newRoomNumberChosen;
    private boolean isBooked;
}
