package odogwuHotels.data.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Room {
    private RoomType roomType;
    private BigDecimal price;
    private int roomNumber;
    private boolean isAvailable = true;
    private int id;
}
