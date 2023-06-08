package odogwuHotels.dto.responses;

import lombok.Data;
import odogwuHotels.data.models.RoomType;

import java.math.BigDecimal;

@Data
public class RoomSearchResponse {
    private RoomType roomType;
    private int roomNumber;
    private int roomId;
    private BigDecimal price;
    private boolean isAvailable;
    private String message;
}
