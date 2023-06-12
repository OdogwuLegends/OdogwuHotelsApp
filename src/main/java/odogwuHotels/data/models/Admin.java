package odogwuHotels.data.models;

import lombok.Data;

@Data
public class Admin extends User{
    private int adminCode;
    private boolean isSuperAdmin = false;
    private boolean approveNewAdmin;
}
