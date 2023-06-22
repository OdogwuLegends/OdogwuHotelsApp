package odogwuHotels.dto.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private boolean isLoggedIn;
    private String email;
    private String password;
    private String message;

    public String toString(){
        return "\n"+message+"\n";
    }
}
