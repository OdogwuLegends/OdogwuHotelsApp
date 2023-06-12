package odogwuHotels.services;

import odogwuHotels.Map;
import odogwuHotels.data.models.Admin;
import odogwuHotels.data.models.Receipt;
import odogwuHotels.data.models.Reservation;
import odogwuHotels.data.repositories.OHReceiptRepository;
import odogwuHotels.data.repositories.ReceiptRepository;
import odogwuHotels.dto.requests.ReservationRequest;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.ReceiptResponse;
import odogwuHotels.dto.responses.ReservationResponse;
import odogwuHotels.dto.responses.UpdateResponse;

import java.math.BigDecimal;
import java.util.List;

public class OHReceiptService implements ReceiptService{
    private final ReceiptRepository receiptRepository = new OHReceiptRepository();
    ReservationService reservationService;
    AdminService adminService;

    @Override
    public String createReceipt(ReservationRequest request, Admin admin) {
        reservationService = new OHReservationService();
        adminService = new OHAdminService();

        ReservationResponse response = reservationService.findReservationByRoomNumber(request);
        Reservation reservation = Map.reservationResToReservation(response);
        Receipt receiptToCreate = Map.createReceipt(reservation,admin);

        receiptToCreate.setAmountPaid(request.getMakePayment());

        if(admin.isSuperAdmin()){
            receiptToCreate.setApproved(true);
            //MORE DETAILS NEEDED ON ADMIN TO APPROVE RECEIPT
            adminService.approvePayment(receiptToCreate);
        }
        receiptToCreate.setBalance(reservation.getRoom().getPrice().subtract(request.getMakePayment()));
       if(receiptToCreate.getBalance().compareTo(BigDecimal.ZERO) <= 0) receiptToCreate.setFullyPaidFor(true);
       receiptRepository.saveReceipt(receiptToCreate);
        return "Receipt created.";
    }

    @Override
    public UpdateResponse editReceiptById(int id) {
        Receipt foundReceipt = receiptRepository.findById(id);
                //DO WE EDIT RECEIPTS OR GENERATE NEW RECEIPTS?

//        if(foundReceipt != null){
//            if(receipt.getFirstName() != null) foundReceipt.setFirstName(receipt.getFirstName());
//            if(receipt.getLastName() != null) foundReceipt.setLastName(receipt.getLastName());
//            if(receipt.getBalance() != null) foundReceipt.setBalance(receipt.getBalance());
//            if(receipt.getApprovedBy() != null) foundReceipt.setApprovedBy(receipt.getApprovedBy());
//            if(receipt.getAmountPaid() != null) foundReceipt.setAmountPaid(receipt.getAmountPaid());
//            if(receipt.getRoomType() != null) foundReceipt.setRoomType(receipt.getRoomType());
//            if(receipt.getRoomPrice() != null) foundReceipt.setRoomPrice(receipt.getRoomPrice());
//            if(receipt.getDurationOfStay() > 0) foundReceipt.setDurationOfStay(receipt.getDurationOfStay());
//            if(receipt.getCheckInDate() != null) foundReceipt.setCheckInDate(receipt.getCheckInDate());
//            if(receipt.getCheckOutDate() != null) foundReceipt.setCheckOutDate(receipt.getCheckOutDate());
//            if(receipt.isFullyPaidFor() != foundReceipt.isFullyPaidFor()) foundReceipt.setFullyPaidFor(receipt.isFullyPaidFor());
//        }
        return null;
    }

    @Override
    public ReceiptResponse findReceiptById(int id) {
        Receipt foundReceipt = receiptRepository.findById(id);
        ReceiptResponse response = Map.receiptToResponse(foundReceipt);
        response.setMessage("Receipt Found");
        return response;
    }

    @Override
    public ReceiptResponse findReceiptByEmail(String email) {
        Receipt foundReceipt = receiptRepository.findByEmail(email);
        ReceiptResponse response = Map.receiptToResponse(foundReceipt);
        response.setMessage("Receipt Found");
        return response;
    }

    @Override
    public ReceiptResponse generateReceiptById(int id){
        Receipt foundReceipt = receiptRepository.findById(id);
        return Map.receiptToResponse(foundReceipt);
    }
    @Override
    public ReceiptResponse generateReceiptByEmail(String email){
        Receipt foundReceipt = receiptRepository.findByEmail(email);
        return Map.receiptToResponse(foundReceipt);
    }

    @Override
    public List<Receipt> findAllReceipts() {
        return receiptRepository.findAllReceipts();
    }

    @Override
    public DeleteResponse deleteReceiptById(int id) {
        receiptRepository.deleteReceiptById(id);
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Receipt Deleted Successfully");
        return response;
    }
}
