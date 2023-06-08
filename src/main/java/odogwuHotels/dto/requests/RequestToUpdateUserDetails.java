package odogwuHotels.dto.requests;

import lombok.Data;
import odogwuHotels.data.models.User;

@Data
public class RequestToUpdateUserDetails extends User {
    private String newEmail;
    private boolean isSuperAdmin;
}
