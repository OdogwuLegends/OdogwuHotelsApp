package odogwuHotels.dto.requests;

import lombok.Data;

@Data
public class RegisterAdminRequest extends RegisterUserRequest{
    private boolean isSuperAdmin;
    private boolean isAuxiliaryAdmin;
}
