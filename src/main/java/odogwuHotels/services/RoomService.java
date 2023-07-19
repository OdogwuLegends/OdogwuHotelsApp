package odogwuHotels.services;

import odogwuHotels.data.models.Room;
import odogwuHotels.dto.requests.RequestToCreateRoom;
import odogwuHotels.dto.requests.RequestToUpdateRoom;
import odogwuHotels.dto.requests.RoomSearchRequest;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.RoomCreationResponse;
import odogwuHotels.dto.responses.SearchResponse;
import odogwuHotels.dto.responses.UpdateResponse;
import odogwuHotels.exceptions.EntityNotFoundException;

import java.util.List;

public interface RoomService {
    RoomCreationResponse createRoom(RequestToCreateRoom createRoom);
    RoomCreationResponse createRoomAuto();
    UpdateResponse editRoomDetails(RequestToUpdateRoom updateRoom) throws EntityNotFoundException;
    SearchResponse findAvailableRooms(RoomSearchRequest request);
    SearchResponse findBookedRooms(RoomSearchRequest request);
    SearchResponse findRoomByIdOrRoomNumber(RoomSearchRequest request) throws EntityNotFoundException;
    List<Room> findAllRooms();
    DeleteResponse deleteRoomByRoomById(RequestToUpdateRoom updateRoom) throws EntityNotFoundException;
    DeleteResponse deleteRoomByRoomByRoomNumber(RequestToUpdateRoom request) throws EntityNotFoundException;
    DeleteResponse deleteAll();


}
