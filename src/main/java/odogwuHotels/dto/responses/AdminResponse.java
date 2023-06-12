package odogwuHotels.dto.responses;

import lombok.Data;

@Data
public class AdminResponse extends UserResponse{
    private boolean isSuperAdmin;
    private boolean approveNewAdmin;
    private int adminCode;
    private String message;
}
