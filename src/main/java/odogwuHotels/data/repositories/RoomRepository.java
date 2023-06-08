package odogwuHotels.data.repositories;

import odogwuHotels.data.models.FindRoomByChoice;
import odogwuHotels.data.models.Room;
import odogwuHotels.dto.requests.RequestToUpdateRoom;

import java.util.List;

public interface RoomRepository {
    Room saveRoom(Room room);
    Room updateRoom(RequestToUpdateRoom updateRoom);
    Room findRoomByRoomNumber(int roomNumber);
    Room findRoomById(int id);
    List<Integer> findAvailableRooms(FindRoomByChoice findRoomByChoice);
    List<Integer> findBookedRooms(FindRoomByChoice findRoomByChoice);
    List<Integer> findAllAvailableRooms();
    List<Integer> findAvailableSingleRooms();
    List<Integer> findAvailableDoubleRooms();
    List<Integer> findAllBookedRooms();
    List<Integer> findBookedSingleRooms();
    List<Integer> findBookedDoubleRooms();
    List<Room> findAllRooms();
    void removeRoomById(int id);

}
