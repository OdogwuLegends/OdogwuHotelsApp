package odogwuHotels.services;

import odogwuHotels.Map;
import odogwuHotels.data.models.Admin;
import odogwuHotels.dto.requests.RegisterAdminRequest;
import odogwuHotels.dto.requests.RequestToUpdateUserDetails;
import odogwuHotels.dto.responses.AdminResponse;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.UpdateResponse;
import odogwuHotels.exceptions.AdminException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OHAdminServiceTest {
    private final AdminService adminService = new OHAdminService();
    AdminResponse superAdmin;
    AdminResponse auxAdmin;

    @BeforeEach
    void setUp(){
        superAdmin = adminService.registerSuperAdmin(mainAdmin());
        auxAdmin = adminService.registerAuxiliaryAdmins(subAdmin(), Map.adminResponseToAdmin(superAdmin));
    }

    @Test
    void registerSuperAdmin(){
        Admin firstAdmin = Map.adminResponseToAdmin(superAdmin);
        Admin secondAdmin = Map.adminResponseToAdmin(auxAdmin);
        assertTrue(firstAdmin.isSuperAdmin());
        assertFalse(secondAdmin.isSuperAdmin());
    }
    @Test
    void registerAuxAdmin(){
        Admin secondAdmin = Map.adminResponseToAdmin(auxAdmin);
        assertFalse(secondAdmin.isSuperAdmin());
    }
    @Test
    void editAdminDetails(){
        assertEquals("Inem",auxAdmin.getFirstName());
        assertEquals("ename@gmail.com",auxAdmin.getEmail());

        RequestToUpdateUserDetails request = new RequestToUpdateUserDetails();
        request.setEmail("ename@gmail.com");
        request.setFirstName("Inyang");
        request.setNewEmail("hello@gmail.com");

        UpdateResponse updatedDetails = adminService.editAdminDetails(request);
        assertEquals(auxAdmin.getId(),updatedDetails.getId());
        assertEquals(auxAdmin.getAdminCode(),updatedDetails.getAdminCode());
        assertEquals("hello@gmail.com",updatedDetails.getEmail());
        assertEquals("Inyang",updatedDetails.getFirstName());
    }
    @Test
    void findAdminById(){
        AdminResponse foundAdmin = new AdminResponse();
        try {
          foundAdmin  = adminService.findAdminById(superAdmin.getId());
        } catch (AdminException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals(superAdmin.getId(),foundAdmin.getId());
        assertEquals(superAdmin.getEmail(),foundAdmin.getEmail());
    }
    @Test
    void errorIfFoundAdminIsNull(){
        Admin newAdmin = new Admin();
        AdminResponse foundAdmin = Map.adminToAdminResponse(newAdmin);

        assertThrows(AdminException.class,()-> adminService.findAdminById(foundAdmin.getId()));
    }
    @Test
    void findAllAdmins(){
        List<Admin> allAdmins = adminService.findAllAdmins();
        assertEquals(2,allAdmins.size());
    }
    @Test
    void deleteAdminById(){
        DeleteResponse deletedAdmin = new DeleteResponse();
        try {
            deletedAdmin = adminService.deleteAdminById(auxAdmin.getId());
        } catch (AdminException ex){
            System.out.println(ex.getMessage());
        }
        assertEquals("Admin Deleted Successfully",deletedAdmin.getMessage());
    }
    @Test
    void nullAdminCannotBeDeleted(){
        Admin newAdmin = new Admin();
        AdminResponse adminToBeDeleted = Map.adminToAdminResponse(newAdmin);

        assertThrows(AdminException.class,()-> adminService.deleteAdminById(adminToBeDeleted.getId()));
    }









    private RegisterAdminRequest mainAdmin(){
        RegisterAdminRequest request = new RegisterAdminRequest();

        request.setFirstName("Legend");
        request.setLastName("Max");
        request.setEmail("leg@gmail.com");
        request.setPassword("1234");

        return request;
    }
    private RegisterAdminRequest subAdmin(){
        RegisterAdminRequest request = new RegisterAdminRequest();

        request.setFirstName("Inem");
        request.setLastName("Udo");
        request.setEmail("ename@gmail.com");
        request.setPassword("2222");

        return request;
    }

}