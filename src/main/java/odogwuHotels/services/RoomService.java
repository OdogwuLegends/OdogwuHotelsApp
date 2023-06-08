package odogwuHotels.services;

import odogwuHotels.data.models.Room;
import odogwuHotels.dto.requests.RequestToCreateRoom;
import odogwuHotels.dto.requests.RequestToUpdateRoom;
import odogwuHotels.dto.requests.RoomSearchRequest;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.RoomCreationResponse;
import odogwuHotels.dto.responses.RoomSearchResponse;
import odogwuHotels.dto.responses.RoomUpdateResponse;

import java.util.List;

public interface RoomService {
    RoomCreationResponse createRoom(RequestToCreateRoom createRoom);
    RoomUpdateResponse editRoomDetails(RequestToUpdateRoom updateRoom);
    RoomSearchResponse findAvailableRooms(RoomSearchRequest request);
    RoomSearchResponse findBookedRooms(RoomSearchRequest request);
    RoomSearchResponse findRoomByIdOrRoomNumber(RoomSearchRequest request);
    List<Room> findAllRooms();
    DeleteResponse deleteRoomByRoomById(RequestToUpdateRoom updateRoom);


}
