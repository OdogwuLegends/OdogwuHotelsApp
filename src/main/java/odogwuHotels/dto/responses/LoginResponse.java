package odogwuHotels.dto.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private boolean isLoggedIn;
    private String message;
}
