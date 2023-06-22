package odogwuHotels.services;

import odogwuHotels.myUtils.Map;
import odogwuHotels.data.models.Admin;
import odogwuHotels.data.models.Receipt;
import odogwuHotels.data.models.Reservation;
import odogwuHotels.data.repositories.OHReceiptRepository;
import odogwuHotels.data.repositories.ReceiptRepository;
import odogwuHotels.dto.requests.ReservationRequest;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.ReceiptResponse;
import odogwuHotels.dto.responses.ReservationResponse;
import odogwuHotels.exceptions.AdminException;
import odogwuHotels.exceptions.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class OHReceiptService implements ReceiptService{
    private final ReceiptRepository receiptRepository = OHReceiptRepository.createObject();
    ReservationService reservationService;
    AdminService adminService;

    @Override
    public ReceiptResponse createReceipt(ReservationRequest request, Admin admin) throws AdminException {
        reservationService = new OHReservationService();
        adminService = new OHAdminService();

        if(!admin.isSuperAdmin()){
            throw new AdminException("Not authorized. Submit to Super Admin to complete task.");
        }
        ReservationResponse response = new ReservationResponse();
        try {
            response = reservationService.findReservationByRoomNumber(request);
        } catch (EntityNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        Reservation reservation = Map.reservationResToReservation(response);
        Receipt receiptToCreate = Map.createReceipt(reservation,admin);

        receiptToCreate.setAmountPaid(request.getMakePayment());
        receiptToCreate.setApproved(true);

        //MORE DETAILS NEEDED ON ADMIN TO APPROVE RECEIPT
        adminService.approvePayment(receiptToCreate);

        BigDecimal balance = BigDecimal.ZERO;
        if (reservation.getRoom() != null && reservation.getRoom().getPrice() != null && request.getMakePayment() != null) {
            balance = reservation.getRoom().getPrice().subtract(request.getMakePayment());
        }
        receiptToCreate.setBalance(balance);

        if(receiptToCreate.getBalance().compareTo(BigDecimal.ZERO) <= 0) receiptToCreate.setFullyPaidFor(true);

       Receipt savedReceipt = receiptRepository.saveReceipt(receiptToCreate);
       ReceiptResponse receiptResponse = Map.receiptToResponse(savedReceipt);
       receiptResponse.setMessage("Receipt Created");
       return receiptResponse;
    }

    @Override
    public ReceiptResponse findReceiptById(int id) throws EntityNotFoundException {
        Receipt foundReceipt = receiptRepository.findById(id);
        if(foundReceipt == null){
            throw new EntityNotFoundException("Receipt not found");
        }
        return Map.receiptToResponse(foundReceipt);
    }

    @Override
    public ReceiptResponse findReceiptByEmail(String email) throws EntityNotFoundException {
        Receipt foundReceipt = receiptRepository.findByEmail(email);
        if(foundReceipt == null){
            throw new EntityNotFoundException("Receipt not found");
        }
        return Map.receiptToResponse(foundReceipt);
    }

    @Override
    public ReceiptResponse generateReceiptById(int id) throws EntityNotFoundException {
        Receipt foundReceipt = receiptRepository.findById(id);
        if(foundReceipt == null){
            throw new EntityNotFoundException("Receipt not found");
        }
        return Map.receiptToResponse(foundReceipt);
    }
    @Override
    public ReceiptResponse generateReceiptByEmail(String email) throws EntityNotFoundException {
        Receipt foundReceipt = receiptRepository.findByEmail(email);
        if(foundReceipt == null){
            throw new EntityNotFoundException("Receipt not found");
        }
        return Map.receiptToResponse(foundReceipt);
    }

    @Override
    public List<Receipt> findAllReceipts() {
        return receiptRepository.findAllReceipts();
    }

    @Override
    public DeleteResponse deleteReceiptById(int id) throws EntityNotFoundException {
        Receipt foundReceipt = receiptRepository.findById(id);
        if(foundReceipt == null){
            throw new EntityNotFoundException("Receipt not found");
        }
        receiptRepository.deleteReceiptById(foundReceipt.getId());
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Receipt Deleted Successfully");
        return response;
    }

    @Override
    public DeleteResponse deleteAll() {
        DeleteResponse response = new DeleteResponse();
        receiptRepository.deleteAll();
        response.setMessage("Delete Successful");
        return response;
    }
}
