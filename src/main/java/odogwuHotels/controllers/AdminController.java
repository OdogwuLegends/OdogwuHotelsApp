package odogwuHotels.controllers;

import odogwuHotels.data.models.*;
import odogwuHotels.dto.requests.*;
import odogwuHotels.dto.responses.*;
import odogwuHotels.exceptions.AdminException;
import odogwuHotels.exceptions.EmailNotCorrectException;
import odogwuHotels.exceptions.EntityNotFoundException;
import odogwuHotels.services.AdminService;
import odogwuHotels.services.OHAdminService;

import java.util.List;

public class AdminController {
    private final AdminService adminService = new OHAdminService();

    public AdminResponse registerSuperAdmin(RegisterAdminRequest request){
        AdminResponse newAdmin = new AdminResponse();
        try {
            newAdmin = adminService.registerSuperAdmin(request);
        } catch (EmailNotCorrectException ex){
            System.err.println(ex.getMessage());
        }
        return newAdmin;
    }
    public AdminResponse registerAuxiliaryAdmins(RegisterAdminRequest request, Admin adminToApprove){
        AdminResponse newAdmin = new AdminResponse();
        try {
            newAdmin = adminService.registerAuxiliaryAdmins(request,adminToApprove);
        } catch (EmailNotCorrectException ex){
            System.err.println(ex.getMessage());
        }
        return newAdmin;
    }
    public UpdateResponse editAdminDetails(RequestToUpdateUserDetails request) throws EmailNotCorrectException {
        return adminService.editAdminDetails(request);
    }
    public AdminResponse findAdminById(int id) throws AdminException{
        return adminService.findAdminById(id);
    }
    public List<Admin> findAllAdmins(){
        return adminService.findAllAdmins();
    }
    public DeleteResponse deleteAdminById(int id) throws AdminException{
        return adminService.deleteAdminById(id);
    }
    public DeleteResponse deleteAllAdmins(){
        return adminService.deleteAllAdmins();
    }
    public RoomCreationResponse createRoom(RequestToCreateRoom request){
        return adminService.createRoom(request);
    }
    public SearchResponse findRoomById(RoomSearchRequest request) throws EntityNotFoundException{
        return adminService.findRoomById(request);
    }
    public SearchResponse findRoomByRoomNumber(RoomSearchRequest request) throws EntityNotFoundException{
        return adminService.findRoomByRoomNumber(request);
    }
    public List<Room> findAllRooms(){
        return adminService.findAllRooms();
    }
    public SearchResponse seeAvailableRooms(RoomSearchRequest request){
        return adminService.seeAvailableRooms(request);
    }
    public SearchResponse seeBookedRooms(RoomSearchRequest request){
        return adminService.seeBookedRooms(request);
    }
    public UpdateResponse editRoomDetails(RequestToUpdateRoom request) throws EntityNotFoundException {
        return adminService.editRoomDetails(request);
    }
    public DeleteResponse deleteRoomById(RequestToUpdateRoom request) throws EntityNotFoundException{
        return adminService.deleteRoomById(request);
    }
    public DeleteResponse deleteRoomByRoomNumber(RequestToUpdateRoom request) throws EntityNotFoundException{
        return adminService.deleteRoomByRoomNumber(request);
    }
    public DeleteResponse deleteAllRooms(){
        return adminService.deleteAllRooms();
    }
    public UserResponse findCustomerByEmail(String email) throws EntityNotFoundException{
        return adminService.findCustomerByEmail(email);
    }
    public UserResponse findCustomerById(int id) throws EntityNotFoundException{
        return adminService.findCustomerById(id);
    }
    public List<Customer> findAllCustomers(){
        return adminService.findAllCustomers();
    }
    public UpdateResponse editCustomerDetails(RequestToUpdateUserDetails request) throws EmailNotCorrectException {
        return adminService.editCustomerDetails(request);
    }
    public DeleteResponse deleteCustomerById(int id) throws EntityNotFoundException{
        return adminService.deleteCustomerById(id);
    }
    public DeleteResponse deleteCustomerByEmail(String email) throws EntityNotFoundException{
        return adminService.deleteCustomerByEmail(email);
    }
    public DeleteResponse deleteAllCustomers(){
        return adminService.deleteAllCustomers();
    }
    public ReservationResponse makeReservation(ReservationRequest request){
        return adminService.makeReservation(request);
    }
    public ReservationResponse findReservationById(int id) throws EntityNotFoundException {
        return adminService.findReservationById(id);
    }
    public ReservationResponse findReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException {
        return adminService.findReservationByRoomNumber(request);
    }
    public List<Reservation> viewAllReservations(){
        return adminService.viewAllReservations();
    }
    public UpdateResponse editReservation(UpdateReservationRequest request){
        return adminService.editReservation(request);
    }
    public DeleteResponse deleteReservationByRoomNumber(ReservationRequest request) throws EntityNotFoundException {
        return adminService.deleteReservationByRoomNumber(request);
    }
    public DeleteResponse deleteReservationById(int id) throws EntityNotFoundException {
        return adminService.deleteReservationById(id);
    }
    public DeleteResponse deleteAllReservations(){
        return adminService.deleteAllReservations();
    }
    public ReceiptResponse createReceipt(ReservationRequest request, Admin admin) throws AdminException{
        return adminService.createReceipt(request, admin);
    }
    public ReceiptResponse issueReceiptsById(int id) throws EntityNotFoundException{
        return adminService.issueReceiptsById(id);
    }
    public ReceiptResponse issueReceiptsByEmail(String email) throws EntityNotFoundException{
        return adminService.issueReceiptsByEmail(email);
    }
    public ReceiptResponse findReceiptById(int id) throws EntityNotFoundException{
        return adminService.findReceiptById(id);
    }
    public List<Receipt> findAllReceipts(){
        return adminService.findAllReceipts();
    }
    public DeleteResponse deleteReceiptById(int id) throws EntityNotFoundException{
        return adminService.deleteReceiptById(id);
    }
    public DeleteResponse deleteAllReceipts(){
        return adminService.deleteAllReceipts();
    }

}
