package odogwuHotels.data.repositories;

import odogwuHotels.myUtils.Utils;
import odogwuHotels.data.models.Customer;
import odogwuHotels.dto.requests.RequestToUpdateUserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OHCustomerRepositoryTest {
    private final CustomerRepository customerRepository =  OHCustomerRepository.createObject();
    Customer firstCustomerSaved;
    Customer secondCustomerSaved;
    @BeforeEach
    void setUp(){
        firstCustomerSaved = customerRepository.saveCustomer(buildFirstCustomer());
        secondCustomerSaved = customerRepository.saveCustomer(buildSecondCustomer());
    }

    @Test
    void saveCustomer(){
        assertNotNull(firstCustomerSaved);
        assertNotNull(secondCustomerSaved);
        List<Customer> allCustomers = customerRepository.findAllCustomers();
        assertEquals(2,allCustomers.size());
        assertTrue(customerRepository.findAllCustomers().contains(firstCustomerSaved));
    }
    @Test
    void updateCustomerDetails(){
        assertEquals("John",firstCustomerSaved.getFirstName());

        int index = customerRepository.getIndex(firstCustomerSaved);

        RequestToUpdateUserDetails request = new RequestToUpdateUserDetails();
        request.setFirstName("Mike");
        request.setLastName("Boyo");
        request.setEmail("doe@gmail.com");
        request.setNewEmail("odogwu@gmail.com");

        firstCustomerSaved.setFirstName(request.getFirstName());
        firstCustomerSaved.setLastName(request.getLastName());
        firstCustomerSaved.setEmail(request.getNewEmail());
        Customer foundCustomer = customerRepository.updateDetails(index,firstCustomerSaved);

        assertEquals("Mike",firstCustomerSaved.getFirstName());
        assertEquals("Boyo",firstCustomerSaved.getLastName());
        assertEquals("odogwu@gmail.com",firstCustomerSaved.getEmail());
        assertSame(firstCustomerSaved,foundCustomer);
    }
    @Test
    void findCustomerByEmail(){
        Customer foundCustomer = customerRepository.findCustomerByEmail("legend@gmail.com");
        assertSame(secondCustomerSaved,foundCustomer);
    }
    @Test
    void findAllCustomers(){
        List<Customer> allCustomers = customerRepository.findAllCustomers();
        assertEquals(2,allCustomers.size());
        assertTrue(customerRepository.findAllCustomers().contains(firstCustomerSaved));
        assertTrue(customerRepository.findAllCustomers().contains(secondCustomerSaved));
    }
    @Test
    void deleteCustomerByEmail(){
        customerRepository.deleteCustomerByEmail("doe@gmail.com");
//        assertTrue(customerRepository.findAllCustomers().contains(firstCustomerSaved));


        List<Customer> allCustomers = customerRepository.findAllCustomers();
//        assertFalse(customerRepository.findAllCustomers().contains(firstCustomerSaved));
        //assertFalse(!allCustomers.contains(firstCustomerSaved));

        assertEquals(1,allCustomers.size());
    }
    @Test
    void deleteCustomerById(){
       customerRepository.deleteCustomerById(secondCustomerSaved.getId());
        List<Customer> allCustomers = customerRepository.findAllCustomers();
        assertEquals(1,allCustomers.size());
    }

    private Customer buildFirstCustomer(){
        Customer newCustomer = new Customer();

        newCustomer.setFirstName("John");
        newCustomer.setLastName("Doe");
        newCustomer.setPassword("1234");
        newCustomer.setEmail("doe@gmail.com");
        newCustomer.setId(Utils.generateId());

        return newCustomer;
    }
    private Customer buildSecondCustomer(){
        Customer newCustomer = new Customer();

        newCustomer.setFirstName("Legend");
        newCustomer.setLastName("Boss");
        newCustomer.setPassword("2222");
        newCustomer.setEmail("legend@gmail.com");
        newCustomer.setId(Utils.generateId());

        return newCustomer;
    }

    @AfterEach
    void cleanUp(){
        customerRepository.deleteCustomerById(firstCustomerSaved.getId());
        customerRepository.deleteCustomerById(secondCustomerSaved.getId());
    }

}