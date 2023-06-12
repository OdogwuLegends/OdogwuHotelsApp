package odogwuHotels.data.repositories;

import odogwuHotels.Utils;
import odogwuHotels.data.models.Customer;

import java.util.*;

public class OHCustomerRepository implements CustomerRepository{

    static List<Customer> customerRepository = new ArrayList<>();

    @Override
    public Customer saveCustomer(Customer newCustomer) {
        newCustomer.setId(Utils.generateId());
        customerRepository.add(newCustomer);
        return newCustomer;
    }

    @Override
    public Customer updateDetails(int index, Customer customerToUpdate) {
        customerRepository.set(index,customerToUpdate);
        return customerToUpdate;
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        for (Customer foundCustomer : customerRepository){
            if(Objects.equals(foundCustomer.getEmail(),email)) return foundCustomer;
        }
        return null;
    }

    @Override
    public Customer findCustomerById(int id) {
        for (Customer foundCustomer : customerRepository){
            if(Objects.equals(foundCustomer.getId(),id)) return foundCustomer;
        }
        return null;
    }

    @Override
    public int getIndex(Customer customerToCheck){
        int index = 0;
        for (Customer customer : customerRepository){
            if(Objects.equals(customer,customerToCheck))
               index  = customerRepository.indexOf(customer);
        }
        return index;
    }
    @Override
    public Map<Integer,Customer> getIdsOfAllCustomers(){
        Map<Integer,Customer> customersId = new TreeMap<>();
        for (Customer customer : customerRepository) {
            customersId.put(customer.getId(), customer);
        }
        return customersId;
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

    @Override
    public void deleteCustomerById(int id) {
        Customer foundCustomer = findCustomerById(id);
        if(foundCustomer != null) customerRepository.remove(foundCustomer);
    }
}
