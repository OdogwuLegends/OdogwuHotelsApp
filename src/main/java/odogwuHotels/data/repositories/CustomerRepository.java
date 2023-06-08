package odogwuHotels.data.repositories;

import odogwuHotels.data.models.Customer;
import odogwuHotels.dto.requests.RequestToUpdateUserDetails;

import java.util.List;

public interface CustomerRepository {
    Customer saveCustomer(Customer newCustomer);
    Customer updateDetails(RequestToUpdateUserDetails request);
    Customer findCustomerByEmail(String email);
    List<Customer> findAllCustomers();
    void deleteCustomerByEmail(String email);
}
