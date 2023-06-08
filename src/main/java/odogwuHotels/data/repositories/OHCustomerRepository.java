package odogwuHotels.data.repositories;

import odogwuHotels.Utils;
import odogwuHotels.data.models.Customer;
import odogwuHotels.dto.requests.RequestToUpdateUserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OHCustomerRepository implements CustomerRepository{

    List<Customer> customerRepository = new ArrayList<>();

    @Override
    public Customer saveCustomer(Customer newCustomer) {
        newCustomer.setId(Utils.generateId());
        customerRepository.add(newCustomer);
        return newCustomer;
    }

    @Override
    public Customer updateDetails(RequestToUpdateUserDetails request) {
        for(Customer foundCustomer : customerRepository){
            if(Objects.equals(foundCustomer.getEmail(),request.getEmail())){
                if(request.getFirstName() != null) foundCustomer.setFirstName(request.getFirstName());
                if(request.getLastName() != null) foundCustomer.setLastName(request.getLastName());
                if(request.getNewEmail() != null) foundCustomer.setEmail(request.getNewEmail());
                if(request.getPassword() != null) foundCustomer.setPassword(request.getPassword());
            }
            return foundCustomer;
        }
        return null;
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        for (Customer foundCustomer : customerRepository){
            if(Objects.equals(foundCustomer.getEmail(),email)) return foundCustomer;
        }
        return null;
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository;
    }

    @Override
    public void deleteCustomerByEmail(String email) {
        Customer foundCustomer = findCustomerByEmail(email);
        if(foundCustomer != null) customerRepository.remove(foundCustomer);
    }
}
