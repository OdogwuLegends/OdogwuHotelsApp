package odogwuHotels.dto.requests;

import lombok.Data;
import odogwuHotels.data.models.FindRoomByType;

@Data
public class RoomSearchRequest {
    private int roomNumberChosen;
    private int roomId;
    private FindRoomByType findRoomByType;
    private boolean isAvailable;
}
