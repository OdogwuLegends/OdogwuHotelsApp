package odogwuHotels.services;

import odogwuHotels.myUtils.Map;
import odogwuHotels.data.models.Room;
import odogwuHotels.data.repositories.OHRoomRepository;
import odogwuHotels.data.repositories.RoomRepository;
import odogwuHotels.dto.requests.RequestToCreateRoom;
import odogwuHotels.dto.requests.RequestToUpdateRoom;
import odogwuHotels.dto.requests.RoomSearchRequest;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.RoomCreationResponse;
import odogwuHotels.dto.responses.SearchResponse;
import odogwuHotels.dto.responses.UpdateResponse;
import odogwuHotels.exceptions.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

import static odogwuHotels.data.models.FindRoomByType.*;
import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;

public class OHRoomService implements RoomService{
    private final RoomRepository roomRepository = OHRoomRepository.createObject();
    SearchResponse response;
    @Override
    public RoomCreationResponse createRoom(RequestToCreateRoom createRoom) {
        Room room = new Room();

        room.setRoomNumber(createRoom.getRoomNumber());
        room.setRoomType(createRoom.getRoomType());
        room.setPrice(createRoom.getPrice());
        room.setAvailable(createRoom.isAvailable());
        Room savedRoom = roomRepository.saveRoom(room);

        RoomCreationResponse response = Map.roomToCreationResponse(savedRoom);
        response.setMessage("Room "+room.getRoomNumber()+" created successfully!");

        return response;
    }

    @Override
    public RoomCreationResponse createRoomAuto() {
        RoomCreationResponse response = new RoomCreationResponse();
        for (int i = 1; i <= 10; i++) {
            Room room = new Room();
            if(i < 6){
                room.setRoomNumber(i);
                room.setAvailable(true);
                room.setRoomType(SINGLE);
                room.setPrice(BigDecimal.valueOf(50));
            } else {
                room.setRoomNumber(i);
                room.setAvailable(true);
                room.setRoomType(DOUBLE);
                room.setPrice(BigDecimal.valueOf(100));
            }
        Room savedRoom = roomRepository.saveRoom(room);
        response = Map.roomToCreationResponse(savedRoom);
        response.setMessage("Room "+room.getRoomNumber()+" created successfully!");
        }
        return response;
    }

    @Override
    public UpdateResponse editRoomDetails(RequestToUpdateRoom request) throws EntityNotFoundException {
        Room roomToUpdate = roomRepository.findRoomByRoomNumber(request.getRoomNumber());
        int index = roomRepository.getIndex(roomToUpdate);
        if(roomToUpdate == null){
            throw new EntityNotFoundException("Room not found");
        }
        if(request.getRoomType() != null) roomToUpdate.setRoomType(request.getRoomType());
        if(request.getNewRoomNumber() > 0) roomToUpdate.setRoomNumber(request.getNewRoomNumber());
        if(request.getPrice()!= null && request.getPrice().compareTo(BigDecimal.ZERO) > 0) roomToUpdate.setPrice(request.getPrice());
        if(request.isAvailable() != roomToUpdate.isAvailable()) roomToUpdate.setAvailable(request.isAvailable());

        Room updatedRoom = roomRepository.updateRoom(index,roomToUpdate);
        UpdateResponse response = Map.roomToUpdateResponse(updatedRoom);
        response.setMessage("Update Successful");
        return response;
    }

    @Override
    public SearchResponse findAvailableRooms(RoomSearchRequest request) {
        response = new SearchResponse();
        if(request.getFindRoomByType() ==  SINGLE_ROOMS) response.setMessage("Available Single Rooms are Room(s) "+Map.listToBuilder(roomRepository.findAvailableRooms(request.getFindRoomByType())));
        if(request.getFindRoomByType() ==  DOUBLE_ROOMS) response.setMessage("Available Double Rooms are Room(s) "+Map.listToBuilder(roomRepository.findAvailableRooms(request.getFindRoomByType())));
        if(request.getFindRoomByType() ==  ALL_ROOMS) response.setMessage("All Available Rooms are Room(s) "+Map.listToBuilder(roomRepository.findAvailableRooms(request.getFindRoomByType())));
        return response;
    }

    @Override
    public SearchResponse findBookedRooms(RoomSearchRequest request) {
        response = new SearchResponse();
        if(request.getFindRoomByType() ==  SINGLE_ROOMS) response.setMessage("Booked Single Rooms are Room(s) "+Map.listToBuilder(roomRepository.findBookedRooms(request.getFindRoomByType())));
        if(request.getFindRoomByType() ==  DOUBLE_ROOMS) response.setMessage("Booked Double Rooms are Room(s) "+Map.listToBuilder(roomRepository.findBookedRooms(request.getFindRoomByType())));
        if(request.getFindRoomByType() ==  ALL_ROOMS) response.setMessage("All Booked Rooms are Room(s) "+Map.listToBuilder(roomRepository.findBookedRooms(request.getFindRoomByType())));
        return response;
    }

    @Override
    public SearchResponse findRoomByIdOrRoomNumber(RoomSearchRequest request) throws EntityNotFoundException {
        response = new SearchResponse();
        if(request.getRoomNumberChosen() > 0){
            Room foundRoom = roomRepository.findRoomByRoomNumber(request.getRoomNumberChosen());
            if(foundRoom == null){
                throw new EntityNotFoundException("Room not found");
            }
            Map.roomToSearchResponse(foundRoom);
            mapRoomToResponse(foundRoom);
            response.setMessage("Room "+foundRoom.getRoomNumber()+" found.");
        } else if (request.getRoomId() > 0) {
            Room foundRoom = roomRepository.findRoomById(request.getRoomId());
            if(foundRoom == null){
                throw new EntityNotFoundException("Room not found");
            }
            Map.roomToSearchResponse(foundRoom);
            mapRoomToResponse(foundRoom);
            response.setMessage("Room "+foundRoom.getRoomNumber()+" found.");
        }
        return response;
    }
    @Override
    public List<Room> findAllRooms() {
        return roomRepository.findAllRooms();
    }
    @Override
    public DeleteResponse deleteRoomByRoomById(RequestToUpdateRoom request) throws EntityNotFoundException {
        Room foundRoom = roomRepository.findRoomById(request.getRoomId());
        if(foundRoom == null){
            throw new EntityNotFoundException("Room not found");
        }
        roomRepository.removeRoomById(foundRoom.getId());
        DeleteResponse response = Map.roomToDeleteResponse(foundRoom);

        response.setMessage("Room "+foundRoom.getRoomNumber()+" Deleted Successfully");
        return response;
    }

    @Override
    public DeleteResponse deleteRoomByRoomByRoomNumber(RequestToUpdateRoom request) throws EntityNotFoundException {
        Room foundRoom = roomRepository.findRoomByRoomNumber(request.getRoomNumber());
        if(foundRoom == null){
            throw new EntityNotFoundException("Room not found");
        }
        roomRepository.removeRoomByRoomNumber(foundRoom.getRoomNumber());
        DeleteResponse response = Map.roomToDeleteResponse(foundRoom);
        response.setMessage("Room "+foundRoom.getRoomNumber()+" Deleted Successfully");
        return response;
    }

    @Override
    public DeleteResponse deleteAll() {
        DeleteResponse response = new DeleteResponse();
        roomRepository.removeAll();
        response.setMessage("Delete Successful");
        return response;
    }

    private void mapRoomToResponse(Room foundRoom) {
        response = new SearchResponse();
        response.setRoomNumber(foundRoom.getRoomNumber());
        response.setRoomId(foundRoom.getId());
        response.setRoomType(foundRoom.getRoomType());
        response.setPrice(foundRoom.getPrice());
        response.setAvailable(foundRoom.isAvailable());
    }

}
