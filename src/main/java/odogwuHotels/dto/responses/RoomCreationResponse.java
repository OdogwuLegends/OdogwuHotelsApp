package odogwuHotels.dto.responses;

import lombok.Data;
import odogwuHotels.data.models.RoomType;

import java.math.BigDecimal;

@Data
public class RoomCreationResponse {
    private RoomType roomType;
    private int roomNumber;
    private int roomId;
    private BigDecimal price;
    private String message;
}
