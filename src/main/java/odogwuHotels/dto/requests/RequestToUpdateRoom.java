package odogwuHotels.dto.requests;

import lombok.Data;

@Data
public class RequestToUpdateRoom extends RequestToCreateRoom{
    private int roomId;
    private int newRoomNumber;
}
