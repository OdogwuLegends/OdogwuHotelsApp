package odogwuHotels.data.repositories;

import odogwuHotels.Utils;
import odogwuHotels.data.models.Admin;
import java.util.*;

public class OHAdminRepository implements AdminRepository {
    static List<Admin> adminRepository = new ArrayList<>();

    @Override
    public Admin saveAdmin(Admin newAdmin) {
        newAdmin.setAdminCode(generateAdminCode());
        newAdmin.setId(Utils.generateId());
        adminRepository.add(newAdmin);
        return newAdmin;
    }

    @Override
    public Admin updateDetails(int index, Admin adminToUpdate) {
        adminRepository.set(index, adminToUpdate);
        return adminToUpdate;
    }
    @Override
    public int getIndex(Admin adminToUpdate){
        int index = 0;
        for (Admin admin : adminRepository){
            if(Objects.equals(admin,adminToUpdate))
                index = adminRepository.indexOf(admin);
        }
        return index;
    }
    @Override
    public Map<Integer, Admin> getIdsOfAllAdmins(){
        Map<Integer,Admin> adminsId = new TreeMap<>();
        for (Admin admin : adminRepository){
            adminsId.put(admin.getId(),admin);
        }
        return adminsId;
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
