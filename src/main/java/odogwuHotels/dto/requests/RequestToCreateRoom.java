package odogwuHotels.dto.requests;

import lombok.Data;
import odogwuHotels.data.models.RoomType;

import java.math.BigDecimal;

@Data
public class RequestToCreateRoom {
    private RoomType roomType;
    private int roomNumber;
    private BigDecimal price;
    private boolean isAvailable = true;
}
