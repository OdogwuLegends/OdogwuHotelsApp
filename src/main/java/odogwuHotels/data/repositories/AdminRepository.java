package odogwuHotels.data.repositories;

import odogwuHotels.data.models.Admin;
import odogwuHotels.dto.requests.RequestToUpdateUserDetails;

import java.util.List;

public interface AdminRepository {
    Admin saveAdmin(Admin newAdmin);
    Admin updateDetails(RequestToUpdateUserDetails request);
    Admin findAdminByEmail(String email);
    Admin findAdminById(int id);
    List<Admin> findAllAdmins();
    void deleteAdminByEmail(String email);
    void deleteAdminById(int id);
}
