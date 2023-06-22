package odogwuHotels.myUtils;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static boolean emailIsCorrect(String email){
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static String validEmail() {
        String email = Display.StringInput("Enter email address: ");
        int trial = 2;
        while(!emailIsCorrect(email)){
            if(trial == 0){
                Display.message("\nLimited exceeded. Try again later.");
                System.exit(3);
            }if(trial == 1){
                Display.errorMessage("Incorrect email format. You have "+trial+" trial left.");
                email = Display.StringInput("\nEnter email: ");
            } else {
                Display.errorMessage("Incorrect email format. You have "+trial+" trials left.");
                email = Display.StringInput("\nEnter email: ");
            }

            trial--;
        }
        return email;
    }
    public static String reconfirmPassword(String password){
        String reconfirmPassword = Display.StringInput("Reconfirm your password: ");

        int trial = 2;
        while (!Objects.equals(password,reconfirmPassword)){
            if(trial == 0){
                Display.message("\nLimited exceeded. Try again later.");
                System.exit(4);
            }

            if(trial == 1){
                Display.errorMessage("Password does not match. You have "+trial+" trial left.");
            } else {
                Display.errorMessage("Password does not match. You have "+trial+" trials left.");
            }
            reconfirmPassword = Display.StringInput("\nReconfirm your password: ");
            trial--;
        }

        return reconfirmPassword;
    }
}
