package odogwuHotels.data.repositories;

import odogwuHotels.Utils;
import odogwuHotels.data.models.FindRoomByChoice;
import odogwuHotels.data.models.Room;
import odogwuHotels.dto.requests.RequestToUpdateRoom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static odogwuHotels.data.models.FindRoomByChoice.*;
import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;

public class OHRoomRepository implements RoomRepository{
    List<Room> roomList = new ArrayList<>();

    public Room saveRoom(Room room){
        room.setId(Utils.generateId());
        roomList.add(room);
        return room;
    }

    @Override
    public Room updateRoom(RequestToUpdateRoom updateRoom) {
        Room foundRoom = findRoomByRoomNumber(updateRoom.getRoomNumber());

        if(foundRoom != null){
            if(updateRoom.getRoomType() != null) foundRoom.setRoomType(updateRoom.getRoomType());
            if(updateRoom.getNewRoomNumber() > 0) foundRoom.setRoomNumber(updateRoom.getNewRoomNumber());
            if(updateRoom.getPrice()!= null && updateRoom.getPrice().compareTo(BigDecimal.ZERO) > 0) foundRoom.setPrice(updateRoom.getPrice());
            if(updateRoom.isAvailable() != foundRoom.isAvailable()) foundRoom.setAvailable(updateRoom.isAvailable());
        }
        return foundRoom;
    }

    @Override
    public Room findRoomByRoomNumber(int roomNumber) {
        for (Room foundRoom : roomList){
            if(Objects.equals(foundRoom.getRoomNumber(),roomNumber)) return foundRoom;
        }
        return null;
    }

    @Override
    public Room findRoomById(int id) {
        for (Room foundRoom : roomList){
            if(Objects.equals(foundRoom.getId(),id)) return foundRoom;
        }
        return null;
    }
    @Override
    public List<Integer> findAvailableRooms(FindRoomByChoice findRoomByChoice){
        List<Integer> rooms = null;

        if(findRoomByChoice != null) {
            if (findRoomByChoice == SINGLE_ROOMS) rooms = new ArrayList<>(findAvailableSingleRooms());
            if (findRoomByChoice == DOUBLE_ROOMS) rooms = new ArrayList<>(findAvailableDoubleRooms());
            if (findRoomByChoice == ALL_ROOMS) rooms = new ArrayList<>(findAllAvailableRooms());
        }
        return rooms;
    }
    @Override
    public List<Integer> findBookedRooms(FindRoomByChoice findRoomByChoice){
        if(findRoomByChoice == SINGLE_ROOMS) return findBookedSingleRooms();
        if(findRoomByChoice == DOUBLE_ROOMS) return findBookedDoubleRooms();
        if(findRoomByChoice == ALL_ROOMS) return findAllBookedRooms();

        return null;
    }

    @Override
    public List<Integer> findAllAvailableRooms() {
        List<Integer> availableRooms = new ArrayList<>();
        for(Room room : roomList){
            if(room.isAvailable()){
                availableRooms.add(room.getRoomNumber());
            }
        }
        return availableRooms;
    }
    public List<Integer> findAvailableSingleRooms(){
        List<Integer> availableSingleRooms = new ArrayList<>();
        for(Room room : roomList){
            if(room.isAvailable() && room.getRoomType() == SINGLE){
                availableSingleRooms.add(room.getRoomNumber());
            }
        }
        return availableSingleRooms;
    }
    public List<Integer> findAvailableDoubleRooms(){
        List<Integer> availableDoubleRooms = new ArrayList<>();
        for(Room room : roomList){
            if(room.isAvailable() && room.getRoomType() == DOUBLE){
                availableDoubleRooms.add(room.getRoomNumber());
            }
        }
        return availableDoubleRooms;
    }

    @Override
    public List<Integer> findAllBookedRooms() {
        List<Integer> bookedRooms = new ArrayList<>();
        for(Room room : roomList){
            if(!room.isAvailable()){
                bookedRooms.add(room.getRoomNumber());
            }
        }
        return bookedRooms;
    }
    public List<Integer> findBookedSingleRooms(){
        List<Integer> bookedSingleRooms = new ArrayList<>();
        for(Room room : roomList){
            if(!room.isAvailable() && room.getRoomType() == SINGLE){
                bookedSingleRooms.add(room.getRoomNumber());
            }
        }
        return bookedSingleRooms;
    }
    public List<Integer> findBookedDoubleRooms(){
        List<Integer> bookedSingleRooms = new ArrayList<>();
        for(Room room : roomList){
            if(!room.isAvailable() && room.getRoomType() == DOUBLE){
                bookedSingleRooms.add(room.getRoomNumber());
            }
        }
        return bookedSingleRooms;
    }

    @Override
    public List<Room> findAllRooms() {
        return roomList;
    }

    @Override
    public void removeRoomById(int id) {
        Room foundRoom = findRoomById(id);
        if(foundRoom!=null) roomList.remove(foundRoom);
    }
}