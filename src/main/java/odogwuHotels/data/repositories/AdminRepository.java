package odogwuHotels.data.repositories;

import odogwuHotels.data.models.Admin;
import odogwuHotels.dto.requests.RequestToUpdateUserDetails;

import java.util.List;
import java.util.Map;

public interface AdminRepository {
    Admin saveAdmin(Admin newAdmin);
    Admin updateDetails(int index, Admin adminToUpdate);
    int getIndex(Admin adminToUpdate);
    Map<Integer, Admin> getIdsOfAllAdmins();
    Admin findAdminByEmail(String email);
    Admin findAdminById(int id);
    List<Admin> findAllAdmins();
    void deleteAdminByEmail(String email);
    void deleteAdminById(int id);
}
