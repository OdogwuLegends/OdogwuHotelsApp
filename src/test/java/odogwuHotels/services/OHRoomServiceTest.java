package odogwuHotels.services;

import odogwuHotels.data.models.Room;
import odogwuHotels.dto.requests.RequestToCreateRoom;
import odogwuHotels.dto.requests.RequestToUpdateRoom;
import odogwuHotels.dto.requests.RoomSearchRequest;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.RoomCreationResponse;
import odogwuHotels.dto.responses.RoomSearchResponse;
import odogwuHotels.dto.responses.RoomUpdateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static odogwuHotels.data.models.FindRoomByChoice.*;
import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

class OHRoomServiceTest {
    private final RoomService roomService = new OHRoomService();
    private RoomCreationResponse singleRoom;
    private RoomCreationResponse doubleRoom;

    @BeforeEach
    void setUp(){
        singleRoom = roomService.createRoom(firstSingleRoomCreated());
        doubleRoom = roomService.createRoom(firstDoubleRoomCreated());
    }

    @Test
    void createSingleRoom(){
        assertNotNull(singleRoom);
        assertEquals("Room 1 created successfully!",singleRoom.getMessage());
    }
    @Test
    void createDoubleRoom(){
        assertNotNull(doubleRoom);
        assertEquals("Room 2 created successfully!",doubleRoom.getMessage());
    }
    @Test
    void changeRoomFromSingleToDouble(){
        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomNumber(1);
        request.setRoomType(DOUBLE);
        RoomUpdateResponse updateRoom = roomService.editRoomDetails(request);
        assertEquals("Update Successful",updateRoom.getMessage());
    }
    @Test
    void editRoomNumber(){
        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomNumber(1);
        request.setNewRoomNumber(5);
        RoomUpdateResponse updateRoom = roomService.editRoomDetails(request);
        assertEquals("Update Successful",updateRoom.getMessage());
    }
    @Test
    void findAvailableSingleRooms(){
        RequestToUpdateRoom updateRoom = new RequestToUpdateRoom();
        updateRoom.setRoomNumber(1);
        updateRoom.setAvailable(true);
        RoomUpdateResponse availableRoom = roomService.editRoomDetails(updateRoom);
        assertEquals("Update Successful",availableRoom.getMessage());

        RoomSearchRequest request = new RoomSearchRequest();
        request.setFindRoomByChoice(SINGLE_ROOMS);

        RoomSearchResponse foundRoom = roomService.findAvailableRooms(request);
        assertEquals("Available Single Rooms are Room(s) 1  ",foundRoom.getMessage());
    }
    @Test
    void findAvailableDoubleRooms(){
        RequestToUpdateRoom updateRoom = new RequestToUpdateRoom();
        updateRoom.setRoomNumber(2);
        updateRoom.setAvailable(true);
        RoomUpdateResponse availableRoom = roomService.editRoomDetails(updateRoom);
        assertEquals("Update Successful",availableRoom.getMessage());

        RoomSearchRequest request = new RoomSearchRequest();
        request.setFindRoomByChoice(DOUBLE_ROOMS);

        RoomSearchResponse foundRoom = roomService.findAvailableRooms(request);
        assertEquals("Available Double Rooms are Room(s) 2  ",foundRoom.getMessage());
    }
    @Test
    void findAllAvailableRooms(){
        RequestToUpdateRoom updateRoom = new RequestToUpdateRoom();
        updateRoom.setRoomNumber(1);
        updateRoom.setAvailable(true);
        RoomUpdateResponse availableRoom = roomService.editRoomDetails(updateRoom);
        assertEquals("Update Successful",availableRoom.getMessage());


        updateRoom.setRoomNumber(2);
        updateRoom.setAvailable(true);
        availableRoom = roomService.editRoomDetails(updateRoom);
        assertEquals("Update Successful",availableRoom.getMessage());

        RoomSearchRequest request = new RoomSearchRequest();
        request.setFindRoomByChoice(ALL_ROOMS);

        RoomSearchResponse foundRoom = roomService.findAvailableRooms(request);
        assertEquals("All Available Rooms are Room(s) 1, 2  ",foundRoom.getMessage());
    }
    @Test
    void findBookedSingleRooms(){
        RoomSearchRequest request = new RoomSearchRequest();
        request.setFindRoomByChoice(SINGLE_ROOMS);

        RoomSearchResponse foundRoom = roomService.findBookedRooms(request);
        assertEquals("Booked Single Rooms are Room(s) 1  ",foundRoom.getMessage());
    }
    @Test
    void findBookedDoubleRooms(){
        RoomSearchRequest request = new RoomSearchRequest();
        request.setFindRoomByChoice(DOUBLE_ROOMS);

        RoomSearchResponse foundRoom = roomService.findBookedRooms(request);
        assertEquals("Booked Double Rooms are Room(s) 2  ",foundRoom.getMessage());
    }
    @Test
    void findAllBookedRooms(){
        RoomSearchRequest request = new RoomSearchRequest();
        request.setFindRoomByChoice(ALL_ROOMS);

        RoomSearchResponse foundRoom = roomService.findBookedRooms(request);
        assertEquals("All Booked Rooms are Room(s) 1, 2  ",foundRoom.getMessage());
    }

    @Test
    void findByRoomNumber(){
        RoomSearchRequest request = new RoomSearchRequest();
        request.setRoomNumberChosen(1);

        RoomSearchResponse foundRoom = roomService.findRoomByIdOrRoomNumber(request);
        assertEquals("Room "+foundRoom.getRoomNumber()+" found.",foundRoom.getMessage());
    }
    @Test
    void findRoomById(){
        RoomSearchRequest request = new RoomSearchRequest();
        //request.setRoomNumberChosen(1);
        //RoomSearchResponse room = roomService.findRoomByIdOrRoomNumber(request);

        //request.setRoomId(room.getRoomId());
        request.setRoomId(1);

        RoomSearchResponse foundRoom = roomService.findRoomByIdOrRoomNumber(request);
        assertEquals("Room "+foundRoom.getRoomId()+" found.",foundRoom.getMessage());
    }
    @Test
    void deleteRoomById(){
        RoomSearchRequest request = new RoomSearchRequest();
        request.setRoomNumberChosen(1);
        RoomSearchResponse foundRoom = roomService.findRoomByIdOrRoomNumber(request);

        RequestToUpdateRoom updateRoom = new RequestToUpdateRoom();
        updateRoom.setRoomId(foundRoom.getRoomId());

        DeleteResponse deletedRoom = roomService.deleteRoomByRoomById(updateRoom);
        assertEquals("Room "+deletedRoom.getRoomNumber()+" Deleted Successfully",deletedRoom.getMessage());
    }
    @Test
    void findAllRooms(){
        List<Room> allRooms = roomService.findAllRooms();
        assertEquals(2,allRooms.size());

    }
    private RequestToCreateRoom firstSingleRoomCreated(){
        RequestToCreateRoom request = new RequestToCreateRoom();

        request.setRoomNumber(1);
        request.setRoomType(SINGLE);
        request.setPrice(BigDecimal.valueOf(50));
        request.setAvailable(false);

        return request;
    }
    private RequestToCreateRoom firstDoubleRoomCreated(){
        RequestToCreateRoom request = new RequestToCreateRoom();

        request.setRoomNumber(2);
        request.setRoomType(DOUBLE);
        request.setPrice(BigDecimal.valueOf(100));
        request.setAvailable(false);

        return request;
    }

}