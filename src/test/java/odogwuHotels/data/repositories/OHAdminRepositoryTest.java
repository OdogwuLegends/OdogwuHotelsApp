package odogwuHotels.data.repositories;

import odogwuHotels.myUtils.Utils;
import odogwuHotels.data.models.Admin;
import odogwuHotels.dto.requests.RequestToUpdateUserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OHAdminRepositoryTest {
    private final AdminRepository adminRepository = OHAdminRepository.createObject();
    private Admin superAdmin;
    private Admin regularAdmin;

    @BeforeEach
    void setUp(){
        superAdmin = adminRepository.saveAdmin(buildFirstAdmin());
        regularAdmin = adminRepository.saveAdmin(buildSecondAdmin());
    }

    @Test
    void saveAdmin(){
        assertNotNull(superAdmin);
        assertNotNull(regularAdmin);
        List<Admin> allAdmins = adminRepository.findAllAdmins();
        assertEquals(2,allAdmins.size());
        assertTrue(allAdmins.contains(superAdmin));
    }
    @Test
    void updateAdminDetails(){
        assertEquals("Henry", regularAdmin.getLastName());
        assertEquals("mark@gmail.com",regularAdmin.getEmail());

        int index = adminRepository.getIndex(regularAdmin);

        RequestToUpdateUserDetails request = new RequestToUpdateUserDetails();
        request.setEmail("mark@gmail.com");
        request.setFirstName("Bravo");
        request.setLastName("Legend");
        request.setNewEmail("legend@gmail.com");

        regularAdmin.setFirstName(request.getFirstName());
        regularAdmin.setLastName(request.getLastName());
        regularAdmin.setEmail(request.getNewEmail());
        Admin updatedAdmin =  adminRepository.updateDetails(index,regularAdmin);

        assertSame(regularAdmin,updatedAdmin);
        assertEquals("Legend", regularAdmin.getLastName());
        assertEquals("legend@gmail.com",regularAdmin.getEmail());
        assertEquals(regularAdmin.getAdminCode(),updatedAdmin.getAdminCode());
    }
    @Test
    void findAdminByEmail(){
        Admin foundAdmin = adminRepository.findAdminByEmail("mark@gmail.com");

        assertSame(regularAdmin,foundAdmin);
        assertEquals(regularAdmin.getEmail(),foundAdmin.getEmail());
        assertEquals(regularAdmin.getFirstName(),foundAdmin.getFirstName());
    }
    @Test
    void findAdminById(){
        Admin foundAdmin = adminRepository.findAdminById(superAdmin.getId());

        assertSame(superAdmin,foundAdmin);
        assertEquals(superAdmin.getPassword(),foundAdmin.getPassword());
        assertEquals(superAdmin.getAdminCode(),foundAdmin.getAdminCode());
    }
    @Test
    void findAllAdmins(){
        List<Admin> allAdmins = adminRepository.findAllAdmins();
        assertEquals(2,allAdmins.size());
        assertTrue(allAdmins.contains(regularAdmin));
    }
    @Test
    void deleteAdminByEmail(){
        adminRepository.deleteAdminByEmail(regularAdmin.getEmail());
        List<Admin> allAdmins = adminRepository.findAllAdmins();
        assertEquals(1,allAdmins.size());
        assertFalse(allAdmins.contains(regularAdmin));
    }
    @Test
    void deleteAdminById(){
        adminRepository.deleteAdminById(superAdmin.getId());
        List<Admin> allAdmins = adminRepository.findAllAdmins();
        assertEquals(1,allAdmins.size());
        assertFalse(allAdmins.contains(superAdmin));
    }


    private Admin buildFirstAdmin(){
        Admin admin = new Admin();

        admin.setFirstName("John");
        admin.setLastName("Cena");
        admin.setAdminCode(3500);
        admin.setId(Utils.generateId());
        admin.setEmail("cena@gmail.com");
        admin.setPassword("1234");
        admin.setSuperAdmin(true);

        return admin;
    }
    private Admin buildSecondAdmin(){
        Admin admin = new Admin();

        admin.setFirstName("Mark");
        admin.setLastName("Henry");
        admin.setAdminCode(4500);
        admin.setId(Utils.generateId());
        admin.setEmail("mark@gmail.com");
        admin.setPassword("2222");
        admin.setSuperAdmin(false);

        return admin;
    }

    @AfterEach
    void cleanUp(){
        adminRepository.deleteAdminById(superAdmin.getId());
        adminRepository.deleteAdminById(regularAdmin.getId());
    }

}