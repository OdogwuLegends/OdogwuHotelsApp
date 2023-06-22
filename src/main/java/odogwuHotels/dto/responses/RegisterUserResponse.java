package odogwuHotels.dto.responses;

import lombok.Data;

@Data
public class RegisterUserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int id;
    private String message;


    @Override
    public String toString(){
        final StringBuffer sb = new StringBuffer();

        sb.append("\nWelcome ").append(firstName).append(" ").append(lastName);
        sb.append("\nYour account has been created successfully.\n");

        return sb.toString();
    }
}
