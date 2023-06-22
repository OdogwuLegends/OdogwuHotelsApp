package odogwuHotels.data.repositories;

import odogwuHotels.myUtils.Utils;
import odogwuHotels.data.models.FindRoomByType;
import odogwuHotels.data.models.Room;
import java.util.*;
import static odogwuHotels.data.models.FindRoomByType.*;
import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;

public class OHRoomRepository implements RoomRepository{
    static List<Room> roomList = new ArrayList<>();
    private static OHRoomRepository singleObject;

    private OHRoomRepository(){

    }
    public static OHRoomRepository createObject(){
        if(singleObject == null){
            singleObject = new OHRoomRepository();
        }
        return singleObject;
    }
    public Room saveRoom(Room room){
        room.setId(Utils.generateId());
        roomList.add(room);
        return room;
    }

    @Override
    public Room updateRoom (int index, Room roomToUpdate) {
        roomList.set(index,roomToUpdate);
        return roomToUpdate;
    }
    @Override
    public int getIndex (Room roomToCheck){
        int index = 0;
        for (Room room : roomList){
            if(Objects.equals(room,roomToCheck))
                index = roomList.indexOf(room);
        }
        return index;
    }
    @Override
    public Map<Integer, Integer> getIdsOfAllRooms(){
        Map<Integer, Integer> idsOfAllRooms = new TreeMap<>();
        for (Room room : roomList){
            idsOfAllRooms.put(room.getId(), room.getRoomNumber());
        }
        return idsOfAllRooms;
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
    public List<Integer> findAvailableRooms(FindRoomByType findRoomByType){
        List<Integer> rooms = null;

        if(findRoomByType != null) {
            if (findRoomByType == SINGLE_ROOMS) rooms = new ArrayList<>(findAvailableSingleRooms());
            if (findRoomByType == DOUBLE_ROOMS) rooms = new ArrayList<>(findAvailableDoubleRooms());
            if (findRoomByType == ALL_ROOMS) rooms = new ArrayList<>(findAllAvailableRooms());
        }
        return rooms;
    }
    @Override
    public List<Integer> findBookedRooms(FindRoomByType findRoomByType){
        if(findRoomByType == SINGLE_ROOMS) return findBookedSingleRooms();
        if(findRoomByType == DOUBLE_ROOMS) return findBookedDoubleRooms();
        if(findRoomByType == ALL_ROOMS) return findAllBookedRooms();

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

    @Override
    public void removeRoomByRoomNumber(int roomNumber) {
        Room foundRoom = findRoomByRoomNumber(roomNumber);
        if(foundRoom!=null) roomList.remove(foundRoom);
    }

    @Override
    public void removeAll() {
        roomList.clear();
    }
}
