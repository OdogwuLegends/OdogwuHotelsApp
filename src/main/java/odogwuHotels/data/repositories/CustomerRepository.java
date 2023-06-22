package odogwuHotels.data.repositories;

import odogwuHotels.data.models.Customer;
import odogwuHotels.dto.requests.RequestToUpdateUserDetails;

import java.util.List;
import java.util.Map;

public interface CustomerRepository {
    Customer saveCustomer(Customer newCustomer);
    Customer updateDetails(int index, Customer customerToUpdate);
    Customer findCustomerByEmail(String email);
    Customer findCustomerById(int id);
    int getIndex(Customer customerToCheck);
    Map<Integer,Customer> getIdsOfAllCustomers();
    List<Customer> findAllCustomers();
    void deleteCustomerByEmail(String email);
    void deleteCustomerById(int id);
    void deleteAll();
}
