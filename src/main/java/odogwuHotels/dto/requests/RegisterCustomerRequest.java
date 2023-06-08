package odogwuHotels.dto.requests;

import lombok.Data;

@Data
public class RegisterCustomerRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
