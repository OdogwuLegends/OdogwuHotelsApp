package odogwuHotels.dto.responses;

import lombok.Data;

@Data
public class CustomerResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int id;
}
