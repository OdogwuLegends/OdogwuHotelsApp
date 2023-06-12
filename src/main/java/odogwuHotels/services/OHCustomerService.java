package odogwuHotels.services;

import odogwuHotels.Map;
import odogwuHotels.data.models.Customer;
import odogwuHotels.data.repositories.CustomerRepository;
import odogwuHotels.data.repositories.OHCustomerRepository;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Objects;

public class OHCustomerService implements CustomerService {
    private final CustomerRepository customerRepository = new OHCustomerRepository();
    RoomService roomService;
    ReservationService reservationService;
    ReceiptService receiptService;

    @Override
    public RegisterUserResponse registerCustomer(RegisterUserRequest request) {
        Customer savedCustomer = customerRepository.saveCustomer(Map.customerRequestToCustomer(request));
        RegisterUserResponse response = Map.customerToRegUserResponse(savedCustomer);
        response.setMessage("Registration Successful");
        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws UserNotFoundException {
        Customer foundCustomer =  customerRepository.findCustomerByEmail(request.getEmail());
        if(foundCustomer == null){
            throw new UserNotFoundException("Customer Not Found");
        }
        LoginResponse response = new LoginResponse();
        if(Objects.equals(foundCustomer.getPassword(),request.getPassword())){
            response.setLoggedIn(true);
        } else {
            response.setMessage("Password Incorrect");
        }
        return response;
    }
    public UserResponse findCustomerByEmail(String email) throws UserNotFoundException {
        Customer foundCustomer = customerRepository.findCustomerByEmail(email);
        if(foundCustomer == null){
            throw new UserNotFoundException("Customer Not Found");
        }
        return Map.customerToCustomerResponse(foundCustomer);
    }

    @Override
    public UserResponse findCustomerById(int id) throws UserNotFoundException {
        Customer foundCustomer = customerRepository.findCustomerById(id);
        if(foundCustomer == null){
            throw new UserNotFoundException("Customer Not Found");
        }
        return Map.customerToCustomerResponse(foundCustomer);
    }

    public List<Customer> findAllCustomers(){
        return customerRepository.findAllCustomers();
    }
    public UpdateResponse updateCustomerDetails(RequestToUpdateUserDetails request){
        Customer customerToUpdate = customerRepository.findCustomerByEmail(request.getEmail());
        int index = customerRepository.getIndex(customerToUpdate);

        if(customerToUpdate != null){
            if(request.getFirstName() != null) customerToUpdate.setFirstName(request.getFirstName());
            if(request.getLastName() != null) customerToUpdate.setLastName(request.getLastName());
            if(request.getNewEmail() != null) customerToUpdate.setEmail(request.getNewEmail());
            if(request.getPassword() != null) customerToUpdate.setPassword(request.getPassword());
        }

        Customer updatedCustomer = customerRepository.updateDetails(index,customerToUpdate);
        UpdateResponse response = Map.customerToUpdateResponse(updatedCustomer);
        response.setMessage("Update Successful");
        return response;
    }
    public DeleteResponse deleteCustomerByEmail(String email){
        DeleteResponse response = new DeleteResponse();
        Customer foundCustomer = customerRepository.findCustomerByEmail(email);
        response.setFirstName(foundCustomer.getFirstName());
        response.setLastName(foundCustomer.getLastName());
        response.setEmail(foundCustomer.getEmail());
        response.setId(foundCustomer.getId());
        response.setPassword(foundCustomer.getPassword());
        response.setMessage("Customer Deleted Successfully");
        customerRepository.deleteCustomerByEmail(foundCustomer.getEmail());
        return response;
    }

    @Override
    public DeleteResponse deleteCustomerById(int id) {
        DeleteResponse response = new DeleteResponse();
        Customer foundCustomer = customerRepository.findCustomerById(id);
        response.setFirstName(foundCustomer.getFirstName());
        response.setLastName(foundCustomer.getLastName());
        response.setEmail(foundCustomer.getEmail());
        response.setId(foundCustomer.getId());
        response.setPassword(foundCustomer.getPassword());
        response.setMessage("Customer Deleted Successfully");
        customerRepository.deleteCustomerById(foundCustomer.getId());
        return response;
    }

    @Override
    public SearchResponse findAvailableRooms(RoomSearchRequest request) {
        roomService = new OHRoomService();
        return roomService.findAvailableRooms(request);
    }

    @Override
    public ReservationResponse makeReservation(ReservationRequest request) {
        reservationService = new OHReservationService();
        return reservationService.makeReservation(request);
    }

    @Override
    public ReservationResponse findReservationByRoomNumber(ReservationRequest request) {
        reservationService = new OHReservationService();
        return reservationService.findReservationByRoomNumber(request);
    }
    @Override
    public UpdateResponse updateReservation(UpdateReservationRequest request){
        reservationService = new OHReservationService();
        return reservationService.updateReservation(request);
    }
    @Override
    public DeleteResponse deleteReservationByRoomNumber(ReservationRequest request) {
        reservationService = new OHReservationService();
        return reservationService.deleteReservationByRoomNumber(request);
    }
    @Override
    public ReservationResponse checkIn(ReceiptRequest request){
        reservationService = new OHReservationService();
        return reservationService.checkIn(request);
    }
    @Override
    public ReservationResponse checkOut(ReceiptRequest request){
        reservationService = new OHReservationService();
        return reservationService.checkOut(request);
    }
    @Override
    public ReceiptResponse requestReceipt(ReceiptRequest request) {
        receiptService = new OHReceiptService();
        return receiptService.generateReceiptByEmail(request.getEmail());
    }
}
