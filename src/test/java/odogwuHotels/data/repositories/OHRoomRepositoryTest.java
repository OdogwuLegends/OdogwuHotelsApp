package odogwuHotels.data.repositories;

import odogwuHotels.data.models.Room;
import odogwuHotels.dto.requests.RequestToUpdateRoom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static odogwuHotels.data.models.FindRoomByChoice.*;
import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

class OHRoomRepositoryTest {
    private final RoomRepository roomRepository = OHRoomRepository.createObject();
    private Room firstRoomSaved;
    private Room secondRoomSaved;

    @BeforeEach
    void setUp(){
       firstRoomSaved = roomRepository.saveRoom(buildFirstRoom());
       secondRoomSaved = roomRepository.saveRoom(buildSecondRoom());
    }

    @Test
    void saveRoom(){
        assertNotNull(firstRoomSaved);
        assertNotNull(secondRoomSaved);

        List<Room> allRooms = roomRepository.findAllRooms();
        assertEquals(2,allRooms.size());
    }
    @Test
    void updateSingleRoom(){
        assertEquals(1,firstRoomSaved.getRoomNumber());
        assertEquals(SINGLE,firstRoomSaved.getRoomType());

        int index = roomRepository.getIndex(firstRoomSaved);

        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomId(firstRoomSaved.getId());
        request.setRoomNumber(1);
        request.setNewRoomNumber(5);
        request.setPrice(BigDecimal.valueOf(100));
        request.setRoomType(DOUBLE);
        request.setAvailable(true);

        firstRoomSaved.setRoomNumber(request.getNewRoomNumber());
        firstRoomSaved.setPrice(request.getPrice());
        firstRoomSaved.setRoomType(request.getRoomType());
        firstRoomSaved.setAvailable(request.isAvailable());

        Room updatedRoom = roomRepository.updateRoom(index,firstRoomSaved);

        assertSame(firstRoomSaved,updatedRoom);
        assertEquals(BigDecimal.valueOf(100),firstRoomSaved.getPrice());
        assertEquals(5,firstRoomSaved.getRoomNumber());
        assertEquals(DOUBLE,firstRoomSaved.getRoomType());
        assertTrue(firstRoomSaved.isAvailable());
    }
    @Test
    void updateDoubleRoom(){
        assertEquals(2,secondRoomSaved.getRoomNumber());
        assertEquals(DOUBLE,secondRoomSaved.getRoomType());

        int index = roomRepository.getIndex(secondRoomSaved);

        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomId(secondRoomSaved.getId());
        request.setRoomNumber(2);
        request.setNewRoomNumber(10);
        request.setPrice(BigDecimal.valueOf(200));
        request.setRoomType(SINGLE);

        secondRoomSaved.setRoomNumber(request.getNewRoomNumber());
        secondRoomSaved.setPrice(request.getPrice());
        secondRoomSaved.setRoomType(request.getRoomType());

        Room updatedRoom = roomRepository.updateRoom(index,secondRoomSaved);

        assertSame(secondRoomSaved,updatedRoom);
        assertEquals(BigDecimal.valueOf(200),secondRoomSaved.getPrice());
        assertEquals(10,secondRoomSaved.getRoomNumber());
        assertEquals(SINGLE,secondRoomSaved.getRoomType());
    }
    @Test
    void findRoomByRoomNumber(){
        Room firstFoundRoom = roomRepository.findRoomByRoomNumber(firstRoomSaved.getRoomNumber());
        Room secondFoundRoom = roomRepository.findRoomByRoomNumber(secondRoomSaved.getRoomNumber());

        assertSame(firstRoomSaved,firstFoundRoom);
        assertSame(secondRoomSaved,secondFoundRoom);
    }
    @Test
    void findRoomById(){
        Room firstFoundRoom = roomRepository.findRoomById(firstRoomSaved.getId());
        Room secondFoundRoom = roomRepository.findRoomById(secondRoomSaved.getId());

        assertSame(firstRoomSaved,firstFoundRoom);
        assertSame(secondRoomSaved,secondFoundRoom);
    }
    @Test
    void findAllRooms(){
        List<Room> allRooms = roomRepository.findAllRooms();
        assertEquals(2,allRooms.size());
        assertTrue(allRooms.contains(firstRoomSaved));
        assertTrue(allRooms.contains(secondRoomSaved));
    }
    @Test
    void findAllAvailableRooms(){
        List<Integer> availableRooms = new ArrayList<>();
        assertEquals(availableRooms,roomRepository.findAllAvailableRooms());

        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomNumber(firstRoomSaved.getRoomNumber());
        request.setAvailable(true);

        firstRoomSaved.setRoomNumber(request.getRoomNumber());
        firstRoomSaved.setAvailable(request.isAvailable());

        int index = roomRepository.getIndex(firstRoomSaved);
        roomRepository.updateRoom(index,firstRoomSaved);

        request.setRoomNumber(secondRoomSaved.getRoomNumber());
        request.setAvailable(true);

        secondRoomSaved.setRoomNumber(request.getRoomNumber());
        secondRoomSaved.setAvailable(request.isAvailable());

        index = roomRepository.getIndex(secondRoomSaved);
        roomRepository.updateRoom(index,secondRoomSaved);

        availableRooms.add(1);
        availableRooms.add(2);
        assertEquals(availableRooms,roomRepository.findAllAvailableRooms());
    }
    @Test
    void findAvailableSingleRooms(){
        List<Integer> availableSingleRooms = new ArrayList<>();
        assertEquals(availableSingleRooms,roomRepository.findAllAvailableRooms());

        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomNumber(1);
        request.setAvailable(true);

        firstRoomSaved.setAvailable(request.isAvailable());
        int index = roomRepository.getIndex(firstRoomSaved);
        roomRepository.updateRoom(index,firstRoomSaved);

        availableSingleRooms.add(1);
        assertEquals(availableSingleRooms,roomRepository.findAvailableSingleRooms());
    }

    @Test
    void availableDoubleRooms(){
        List<Integer> availableDoubleRooms = new ArrayList<>();
        assertEquals(availableDoubleRooms,roomRepository.findAllAvailableRooms());

        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomNumber(2);
        request.setAvailable(true);

        secondRoomSaved.setAvailable(request.isAvailable());
        int index = roomRepository.getIndex(secondRoomSaved);
        roomRepository.updateRoom(index,secondRoomSaved);

        availableDoubleRooms.add(2);
        assertEquals(availableDoubleRooms,roomRepository.findAvailableDoubleRooms());

    }
    @Test
    void findAllBookedRooms(){
        List<Integer> bookedRooms = new ArrayList<>(List.of(1,2));
        assertEquals(bookedRooms,roomRepository.findAllBookedRooms());
    }
    @Test
    void findAllBookedSingleRooms(){
        List<Integer> bookedDoubleRooms = new ArrayList<>();
        bookedDoubleRooms.add(1);
        assertEquals(bookedDoubleRooms,roomRepository.findBookedSingleRooms());
    }
    @Test
    void findAllBookedDoubleRooms(){
        List<Integer> bookedDoubleRooms = new ArrayList<>();
        bookedDoubleRooms.add(2);
        assertEquals(bookedDoubleRooms,roomRepository.findBookedDoubleRooms());
    }
    @Test
    void findAvailableRoomsByIndicatingType(){
        List<Integer> availableSingleRooms = new ArrayList<>();
        RequestToUpdateRoom request = new RequestToUpdateRoom();
        request.setRoomNumber(1);
        request.setAvailable(true);

        firstRoomSaved.setAvailable(request.isAvailable());
        int index = roomRepository.getIndex(firstRoomSaved);
        roomRepository.updateRoom(index,firstRoomSaved);

        availableSingleRooms.add(1);
        assertEquals(availableSingleRooms,roomRepository.findAvailableRooms(SINGLE_ROOMS));

        List<Integer> availableDoubleRooms = new ArrayList<>();
        RequestToUpdateRoom request2 = new RequestToUpdateRoom();
        request2.setRoomNumber(2);
        request2.setAvailable(true);

        secondRoomSaved.setAvailable(request.isAvailable());
        index = roomRepository.getIndex(secondRoomSaved);
        roomRepository.updateRoom(index,secondRoomSaved);

        availableDoubleRooms.add(2);
        assertEquals(availableDoubleRooms,roomRepository.findAvailableRooms(DOUBLE_ROOMS));

        List<Integer> allAvailableRooms = new ArrayList<>(List.of(1,2));
        assertEquals(allAvailableRooms,roomRepository.findAvailableRooms(ALL_ROOMS));
    }
    @Test
    void findBookedRoomsByIndicatingType(){
        List<Integer> bookedSingleRooms = new ArrayList<>();
        bookedSingleRooms.add(1);
        assertEquals(bookedSingleRooms,roomRepository.findBookedRooms(SINGLE_ROOMS));

        List<Integer> bookedDoubleRooms = new ArrayList<>();
        bookedDoubleRooms.add(2);
        assertEquals(bookedDoubleRooms,roomRepository.findBookedRooms(DOUBLE_ROOMS));

        List<Integer> allBookedRooms = new ArrayList<>(List.of(1,2));
        assertEquals(allBookedRooms,roomRepository.findBookedRooms(ALL_ROOMS));
    }

    @Test
    void removeRoomById(){
        roomRepository.removeRoomById(secondRoomSaved.getId());

        List<Room> allRooms = roomRepository.findAllRooms();
        assertEquals(1,allRooms.size());
    }



    private Room buildFirstRoom(){
        Room room = new Room();

        room.setAvailable(false);
        room.setRoomNumber(1);
        room.setRoomType(SINGLE);
        room.setPrice(BigDecimal.valueOf(50));

        return room;
    }
    private Room buildSecondRoom(){
        Room room = new Room();

        room.setAvailable(false);
        room.setRoomNumber(2);
        room.setRoomType(DOUBLE);
        room.setPrice(BigDecimal.valueOf(100));

        return room;
    }

    @AfterEach
    void cleanUp(){
        roomRepository.removeRoomById(firstRoomSaved.getId());
        roomRepository.removeRoomById(secondRoomSaved.getId());
    }

}