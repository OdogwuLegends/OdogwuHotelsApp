package odogwuHotels.dto.requests;

import lombok.Data;
import odogwuHotels.data.models.FindRoomByChoice;

@Data
public class RoomSearchRequest {
    private int roomNumberChosen;
    private int roomId;
    private FindRoomByChoice findRoomByChoice;
    private boolean isAvailable;
}
