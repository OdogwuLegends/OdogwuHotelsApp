package odogwuHotels;

import odogwuHotels.controllers.AdminController;
import odogwuHotels.controllers.CustomerController;
import odogwuHotels.data.models.FindRoomByType;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.LoginResponse;
import odogwuHotels.exceptions.EmailNotCorrectException;
import odogwuHotels.exceptions.EntityNotFoundException;
import odogwuHotels.myUtils.Display;
import odogwuHotels.myUtils.Utils;

import java.math.BigDecimal;

import static odogwuHotels.data.models.FindRoomByType.DOUBLE_ROOMS;
import static odogwuHotels.data.models.FindRoomByType.SINGLE_ROOMS;

public class Main {
    private static final AdminController adminController = new AdminController();
    private static final CustomerController customerController = new CustomerController();
    private static LoginResponse loggedInCustomer;

    public static void main(String[] args) {
        mainMenu();
    }

    private static void mainMenu(){
        loggedInCustomer = logIn();
        Display.message("""
                1. Customer
                2. Reservations
                3. Admin
                4. Exit
                """);
        int customerChoice = Display.intInput("");

        switch (customerChoice){
            case 1 -> customer();
            case 2 -> reservations();
//            case 3 -> admin();
            case 4 -> System.exit(1);
        }
    }

    private static void customer() {
        Display.message("""
                1. Create Account
                2. Edit details
                3. Give Feedback
                4. Home Page
                5. Exit
                """);
        int customerChoice = Display.intInput("");
        switch (customerChoice){
            case 1 -> createCustomerAccount();
            case 2 -> editCustomerDetails();
            case 3 -> customerFeedBack();
            case 4 -> mainMenu();
            case 5 -> System.exit(2);
        }
    }

    private static void createCustomerAccount() {
        Display.message("Please fill the following fields correctly; ");

        String firstName = Display.StringInput("Enter First Name: ");
        String lastName = Display.StringInput("Enter Last Name: ");
        String email = Utils.validEmail();
        String password = Display.StringInput("Set password: ");
        String reconfirmPassword = Utils.reconfirmPassword(password);

        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        request.setPassword(reconfirmPassword);

        Display.message(customerController.registerCustomer(request).getMessage());
        mainMenu();
    }
    private static void editCustomerDetails() {
        RequestToUpdateUserDetails request = new RequestToUpdateUserDetails();
        String initialEmail = Display.StringInput("Enter email: ");
        request.setEmail(initialEmail);

        Display.message("Please select and fill the fields to be updated correctly.");
        Display.message("""
                1. First Name
                2. Last Name
                3. Email
                4. Password
                5. Home Page
                6. Exit
                """);
        switch (Display.intInput("")) {
            case 1 -> {
                String firstName = Display.StringInput("Enter First Name: ");
                request.setFirstName(firstName);
            }
            case 2 -> {
                String lastName = Display.StringInput("Enter Last Name: ");
                request.setLastName(lastName);
            }
            case 3 -> {
                String email = Utils.validEmail();
                request.setNewEmail(email);
            }
            case 4 -> {
                String password = Display.StringInput("Set new password: ");
                String reconfirmPassword = Utils.reconfirmPassword(password);
                request.setPassword(reconfirmPassword);
            }
            case 5 -> mainMenu();
            case 6 -> System.exit(5);
        }
        try {
        Display.message(customerController.updateCustomerDetails(request).toString());
        } catch (EmailNotCorrectException ex){
            System.err.println(ex.getMessage());
        }
        moreUpdates();
        mainMenu();
    }

    private static void moreUpdates(){
        Display.message("Would you like to make any more updates?");
        String response = Display.StringInput("");
        while (response.equalsIgnoreCase("Yes")){
            editCustomerDetails();
        }
    }
    private static void customerFeedBack() {
        String feedBack = Display.StringInput("Please provide your feed back here: ");
        Display.message(customerController.giveFeedBack(feedBack).toString());
        mainMenu();
    }
    private static void reservations() {
        Display.message("""
                1. Find and Reserve a Room
                2. See My Reservation
                3. Edit Reservation
                4. Delete Reservation
                5. Generate Receipt
                6. Home Page
                7. Exit
                """);
        switch (Display.intInput("")){
            case 1 -> findRoomsAndMakeReservation();
            case 2 -> seeMyReservation();
//            case 3 -> editReservation();
//            case 4 -> deleteReservation();
//            case 5 -> generateReceipt();
            case 6 -> mainMenu();
            case 7 -> System.exit(6);
        }
    }

    private static void findRoomsAndMakeReservation() {
        int response = Display.intInput("""
                Room Type
                1. Single Room || 2. Double Room
                """);

        FindRoomByType roomType = null;
        if(response == 1){
            roomType = SINGLE_ROOMS;
        } else if (response == 2) {
            roomType = DOUBLE_ROOMS;
        }
        RoomSearchRequest request = new RoomSearchRequest();
        request.setFindRoomByType(roomType);
        Display.message(customerController.findAvailableRooms(request).getMessage());

        String userResponse = Display.StringInput("\nDo you wish to proceed? Enter Yes/No: ");

        ReservationRequest reservationRequest = new ReservationRequest();
        if(userResponse.equalsIgnoreCase("Yes")){
            int roomNumber = Display.intInput("Enter Room Number: ");
            String checkIn = Display.StringInput("Enter Check in date(DD/MM/YYYY): ");
            while (checkIn.length() != 10){
                Display.message("\nWrong input. Please use the correct format.");
                checkIn = Display.StringInput("Enter Check in date(DD/MM/YYYY): ");
            }
            String checkOut = Display.StringInput("Enter Check out date(DD/MM/YYYY): ");
            while (checkOut.length() != 10){
                Display.message("\nWrong input. Please use the correct format.");
                checkOut = Display.StringInput("Enter Check in date(DD/MM/YYYY): ");
            }
            if(roomType == SINGLE_ROOMS){
                BigDecimal payment = Display.bigDecimalInput("Make Payment($50): ");
                reservationRequest.setMakePayment(payment);
            } else if (roomType == DOUBLE_ROOMS) {
                BigDecimal payment = Display.bigDecimalInput("Make Payment($100): ");
                reservationRequest.setMakePayment(payment);
            }

            reservationRequest.setRoomNumberChosen(roomNumber);
            reservationRequest.setCheckInDate(checkIn);
            reservationRequest.setCheckOutDate(checkOut);
            reservationRequest.setEmail(loggedInCustomer.getEmail());

            //CREATE A NEW RECEIPT
            Display.message(customerController.makeReservation(reservationRequest).getMessage());
        }
    }
    private static void seeMyReservation() {
        ReservationRequest request = new ReservationRequest();
        int roomNumber = Display.intInput("Please enter your room number: ");
        request.setRoomNumberChosen(roomNumber);

        try {
        Display.message(customerController.findReservationByRoomNumber(request).toString());
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
    }
//    private static void editReservationDetails() {
//        UpdateReservationRequest request = new UpdateReservationRequest();
//        int roomNumber = Display.intInput("Enter Room number: ");
//
//        request.setRoomNumberChosen(roomNumber);
//
//        //CREATE A NEW RECEIPT AND UPDATE PAYMENT
//        Display.message("Please select and fill the fields to be updated correctly.");
//        Display.message("""
//                1. Check In Date
//                2. Check Out Date
//                3. Update Payment
//                4. New Room
//                5. Reservations
//                6. Home Page
//                7. Exit
//                """);
//        switch (Display.intInput("")) {
//            case 1 -> {
//                String checkIn = Display.StringInput("Enter Check in date(DD/MM/YYYY): ");
//                while (checkIn.length() != 10){
//                    Display.message("\nWrong input. Please use the correct format.");
//                    checkIn = Display.StringInput("Enter Check in date(DD/MM/YYYY): ");
//                    request.setCheckInDate(checkIn);
//            }
//        }
//        case 2 -> {
//                String checkOut = Display.StringInput("Enter Check out date(DD/MM/YYYY): ");
//                while (checkOut.length() != 10){
//                    Display.message("\nWrong input. Please use the correct format.");
//                    checkOut = Display.StringInput("Enter Check in date(DD/MM/YYYY): ");
//                    request.setCheckOutDate(checkOut);
//                }
//            }
//        case 3 -> {
//            BigDecimal payment = Display.bigDecimalInput("Make Payment: ");
//            request.setMakePayment(payment);
//            }
//        case 4 ->{
//            roomNumber = Display.intInput("Enter New Room number: ");
//            request.setNewRoomNumberChosen(roomNumber);
//            }
//        case 5 ->{ reservations(); }
//        case 6 -> { mainMenu(); }
//        case 7 -> System.exit(7);
//        }
//
//
//        try {
//            Display.message(customerController.updateCustomerDetails(request).toString());
//        } catch (EmailNotCorrectException ex){
//            System.err.println(ex.getMessage());
//        }
//        moreUpdates();
//        mainMenu();
//    }
//
////    private static void moreUpdates(){
////        Display.message("Would you like to make any more updates?");
////        String response = Display.StringInput("");
////        while (response.equalsIgnoreCase("Yes")){
////            editCustomerDetails();
////        }
//    }
    private static LoginResponse logIn(){
        Display.message("\nPlease sign in below to continue; \n");
        String email = Utils.validEmail();
        String password = Display.StringInput("Enter Password");

        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);

        LoginResponse foundCustomer = new LoginResponse();
        try {
            foundCustomer = customerController.login(request);
        } catch (EntityNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        return foundCustomer;
    }
}
