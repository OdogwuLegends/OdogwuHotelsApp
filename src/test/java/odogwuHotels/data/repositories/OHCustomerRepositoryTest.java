package odogwuHotels.data.repositories;

import odogwuHotels.Utils;
import odogwuHotels.data.models.Customer;
import odogwuHotels.dto.requests.RequestToUpdateUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OHCustomerRepositoryTest {
    private final CustomerRepository customerRepository = new OHCustomerRepository();
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

        RequestToUpdateUserDetails request = new RequestToUpdateUserDetails();
        request.setFirstName("Mike");
        request.setLastName("Boyo");
        request.setEmail("doe@gmail.com");
        request.setNewEmail("odogwu@gmail.com");
        Customer foundCustomer = customerRepository.updateDetails(request);

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
//        assertFalse(allCustomers.contains(firstCustomerSaved));

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

}