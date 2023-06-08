package odogwuHotels.services;

import odogwuHotels.Map;
import odogwuHotels.data.models.Room;
import odogwuHotels.data.repositories.OHRoomRepository;
import odogwuHotels.data.repositories.RoomRepository;
import odogwuHotels.dto.requests.RequestToCreateRoom;
import odogwuHotels.dto.requests.RequestToUpdateRoom;
import odogwuHotels.dto.requests.RoomSearchRequest;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.RoomCreationResponse;
import odogwuHotels.dto.responses.RoomSearchResponse;
import odogwuHotels.dto.responses.RoomUpdateResponse;

import java.util.List;

import static odogwuHotels.data.models.FindRoomByChoice.*;

public class OHRoomService implements RoomService{
    private final RoomRepository roomRepository = new OHRoomRepository();
    RoomSearchResponse response = new RoomSearchResponse();

    @Override
    public RoomCreationResponse createRoom(RequestToCreateRoom createRoom) {
        Room room = new Room();

        room.setRoomNumber(createRoom.getRoomNumber());
        room.setRoomType(createRoom.getRoomType());
        room.setPrice(createRoom.getPrice());
        room.setAvailable(createRoom.isAvailable());
        roomRepository.saveRoom(room);

        RoomCreationResponse response = new RoomCreationResponse();
        response.setMessage("Room "+room.getRoomNumber()+" created successfully!");
        return response;
    }

    @Override
    public RoomUpdateResponse editRoomDetails(RequestToUpdateRoom updateRoom) {
        roomRepository.updateRoom(updateRoom);
        RoomUpdateResponse response = new RoomUpdateResponse();
        response.setMessage("Update Successful");
        return response;
    }

    @Override
    public RoomSearchResponse findAvailableRooms(RoomSearchRequest request) {
        if(request.getFindRoomByChoice() ==  SINGLE_ROOMS) response.setMessage("Available Single Rooms are Room(s) "+Map.listToBuilder(roomRepository.findAvailableRooms(request.getFindRoomByChoice())));
        if(request.getFindRoomByChoice() ==  DOUBLE_ROOMS) response.setMessage("Available Double Rooms are Room(s) "+Map.listToBuilder(roomRepository.findAvailableRooms(request.getFindRoomByChoice())));
        if(request.getFindRoomByChoice() ==  ALL_ROOMS) response.setMessage("All Available Rooms are Room(s) "+Map.listToBuilder(roomRepository.findAvailableRooms(request.getFindRoomByChoice())));
        return response;
    }

    @Override
    public RoomSearchResponse findBookedRooms(RoomSearchRequest request) {
        if(request.getFindRoomByChoice() ==  SINGLE_ROOMS) response.setMessage("Booked Single Rooms are Room(s) "+Map.listToBuilder(roomRepository.findBookedRooms(request.getFindRoomByChoice())));
        if(request.getFindRoomByChoice() ==  DOUBLE_ROOMS) response.setMessage("Booked Double Rooms are Room(s) "+Map.listToBuilder(roomRepository.findBookedRooms(request.getFindRoomByChoice())));
        if(request.getFindRoomByChoice() ==  ALL_ROOMS) response.setMessage("All Booked Rooms are Room(s) "+Map.listToBuilder(roomRepository.findBookedRooms(request.getFindRoomByChoice())));
        return response;
    }

    @Override
    public RoomSearchResponse findRoomByIdOrRoomNumber(RoomSearchRequest request) {
        if(request.getRoomNumberChosen() > 0){
            Room foundRoom = roomRepository.findRoomByRoomNumber(request.getRoomNumberChosen());
            mapRoomToResponse(foundRoom);
            response.setMessage("Room "+response.getRoomNumber()+" found.");
        } else if (request.getRoomId() > 0) {
            Room foundRoom = roomRepository.findRoomById(request.getRoomId());
            mapRoomToResponse(foundRoom);
            response.setMessage("Room "+foundRoom.getId()+" found.");
        }
        return response;
    }
    @Override
    public List<Room> findAllRooms() {
        return roomRepository.findAllRooms();
    }
    @Override
    public DeleteResponse deleteRoomByRoomById(RequestToUpdateRoom updateRoom) {
        Room foundRoom = roomRepository.findRoomById(updateRoom.getRoomId());

        DeleteResponse response = new DeleteResponse();
        response.setRoomNumber(foundRoom.getRoomNumber());

        roomRepository.removeRoomById(updateRoom.getRoomId());
        response.setMessage("Room "+response.getRoomNumber()+" Deleted Successfully");
        return response;
    }
    private void mapRoomToResponse(Room foundRoom) {
        response.setRoomNumber(foundRoom.getRoomNumber());
        response.setRoomId(foundRoom.getId());
        response.setRoomType(foundRoom.getRoomType());
        response.setPrice(foundRoom.getPrice());
        response.setAvailable(foundRoom.isAvailable());
    }

}
