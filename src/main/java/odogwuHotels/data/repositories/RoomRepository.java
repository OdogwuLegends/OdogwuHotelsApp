package odogwuHotels.data.repositories;

import odogwuHotels.data.models.FindRoomByType;
import odogwuHotels.data.models.Room;

import java.util.List;
import java.util.Map;

public interface RoomRepository {
    Room saveRoom(Room room);
    Room updateRoom(int index, Room roomToUpdate);
    int getIndex (Room roomToCheck);
    Map<Integer, Integer> getIdsOfAllRooms();
    Room findRoomByRoomNumber(int roomNumber);
    Room findRoomById(int id);
    List<Integer> findAvailableRooms(FindRoomByType findRoomByType);
    List<Integer> findBookedRooms(FindRoomByType findRoomByType);
    List<Integer> findAllAvailableRooms();
    List<Integer> findAvailableSingleRooms();
    List<Integer> findAvailableDoubleRooms();
    List<Integer> findAllBookedRooms();
    List<Integer> findBookedSingleRooms();
    List<Integer> findBookedDoubleRooms();
    List<Room> findAllRooms();
    void removeRoomById(int id);
    void removeRoomByRoomNumber(int roomNumber);
    void removeAll();

}
