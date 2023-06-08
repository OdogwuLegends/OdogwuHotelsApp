package odogwuHotels;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class Utils {
    private static  int currentId;



    public static int generateId(){
        currentId += 1;
        return currentId;
    }
    public static LocalDate stringToLocalDate(String userInput) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(userInput, dateFormatter);
    }
    public static String localDateToString(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(dateFormatter);
    }

    public static String durationOfStay(long duration){
        String convertedValue = String.valueOf(duration);
        StringBuilder newValue = new StringBuilder();
        for (int i = 0; i < convertedValue.length(); i++) {
            if(convertedValue.charAt(i) != '-') newValue.append(convertedValue.charAt(i));
        }
        return newValue.toString();
    }
//    public List<Integer> findAvailableRooms(FindRoomByChoice choice) {
//        List<Integer> roomNumber = new ArrayList<>();
//
//        if(choice == SINGLE_ROOMS){
//            //List<Integer> availableSingleRooms = new ArrayList<>();
//            for (Room room : roomList) {
//                if (room.isAvailable() && room.getRoomType() == SINGLE) {
//                    //availableSingleRooms.add(room.getRoomNumber());
//                    roomNumber.add(room.getRoomNumber());
//                }
//                //return availableSingleRooms;
//            }
//        } else if (choice == DOUBLE_ROOMS) {
//            //List<Integer> availableDoubleRooms = new ArrayList<>();
//            for (Room room : roomList) {
//                if (room.isAvailable() && room.getRoomType() == DOUBLE) {
//                    //availableDoubleRooms.add(room.getRoomNumber());
//                    roomNumber.add(room.getRoomNumber());
//                }
//                //return availableDoubleRooms;
//            }
//        }
//        else if(choice == ALL_ROOMS){
//            //List<Integer> availableRooms = new ArrayList<>();
//            for (Room room : roomList) {
//                if (room.isAvailable()) {
//                    //availableRooms.add(room.getRoomNumber());
//                    roomNumber.add(room.getRoomNumber());
//                }
//                //return availableRooms;
//            }
//        }
//        return roomNumber;
//    }
//
//    @Override
//    public List<Integer> findBookedRooms(FindRoomByChoice choice) {
//        if(choice == SINGLE_ROOMS){
//            List<Integer> bookedSingleRooms = new ArrayList<>();
//            for (Room room : roomList) {
//                if (!room.isAvailable() && room.getRoomType() == SINGLE) {
//                    bookedSingleRooms.add(room.getRoomNumber());
//                }
//                return bookedSingleRooms;
//            }
//        } else if (choice == DOUBLE_ROOMS) {
//            List<Integer> bookedDoubleRooms = new ArrayList<>();
//            for (Room room : roomList) {
//                if (!room.isAvailable() && room.getRoomType() == DOUBLE) {
//                    bookedDoubleRooms.add(room.getRoomNumber());
//                }
//                return bookedDoubleRooms;
//            }
//        }
//        else if(choice == ALL_ROOMS){
//            List<Integer> bookedRooms = new ArrayList<>();
//            for (Room room : roomList) {
//                if (!room.isAvailable()) {
//                    bookedRooms.add(room.getRoomNumber());
//                }
//                return bookedRooms;
//            }
//        }
//        return null;
//    }
}
