package odogwuHotels.data.repositories;

import odogwuHotels.Utils;
import odogwuHotels.data.models.Admin;
import odogwuHotels.dto.requests.RequestToUpdateUserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class OHAdminRepository implements AdminRepository {
    List<Admin> adminRepository = new ArrayList<>();

    @Override
    public Admin saveAdmin(Admin newAdmin) {
        newAdmin.setAdminCode(generateAdminCode());
        newAdmin.setId(Utils.generateId());
        adminRepository.add(newAdmin);
        return newAdmin;
    }

    @Override
    public Admin updateDetails(RequestToUpdateUserDetails request) {
        Admin foundAdmin = findAdminByEmail(request.getEmail());
        if(foundAdmin != null){
            if(request.getFirstName() != null) foundAdmin.setFirstName(request.getFirstName());
            if(request.getLastName() != null) foundAdmin.setLastName(request.getLastName());
            if(request.getNewEmail() != null) foundAdmin.setEmail(request.getNewEmail());
            if(request.getPassword() != null) foundAdmin.setPassword(request.getPassword());
            if(request.isSuperAdmin() != foundAdmin.isSuperAdmin()) foundAdmin.setSuperAdmin(request.isSuperAdmin());
            return foundAdmin;
        }
        return null;
    }

    @Override
    public Admin findAdminByEmail(String email) {
        for(Admin foundAmin : adminRepository){
            if(Objects.equals(foundAmin.getEmail(),email)) return foundAmin;
        }
        return null;
    }

    @Override
    public Admin findAdminById(int id) {
        for(Admin foundAmin : adminRepository){
            if(Objects.equals(foundAmin.getId(),id)) return foundAmin;
        }
        return null;
    }

    @Override
    public List<Admin> findAllAdmins() {
        return adminRepository;
    }

    @Override
    public void deleteAdminByEmail(String email) {
        Admin foundAdmin = findAdminByEmail(email);
        if(foundAdmin != null) adminRepository.remove(foundAdmin);
    }

    @Override
    public void deleteAdminById(int id) {
        Admin foundAdmin = findAdminById(id);
        if(foundAdmin != null) adminRepository.remove(foundAdmin);
    }

    private int generateAdminCode(){
        Random random = new Random();
        int code = 0;
        while(code < 1000){
            code = random.nextInt(9999);
        }
        return code;
    }
}
