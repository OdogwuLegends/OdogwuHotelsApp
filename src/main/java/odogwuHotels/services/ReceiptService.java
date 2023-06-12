package odogwuHotels.services;

import odogwuHotels.data.models.Admin;
import odogwuHotels.data.models.Receipt;
import odogwuHotels.data.models.Reservation;
import odogwuHotels.dto.requests.ReservationRequest;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.ReceiptResponse;
import odogwuHotels.dto.responses.UpdateResponse;

import java.util.List;

public interface ReceiptService {
    String createReceipt(ReservationRequest request, Admin admin);
    UpdateResponse editReceiptById(int id);
    ReceiptResponse findReceiptById(int id);
    ReceiptResponse findReceiptByEmail(String email);
    ReceiptResponse generateReceiptById(int id);
    ReceiptResponse generateReceiptByEmail(String email);
    List<Receipt> findAllReceipts();
    DeleteResponse deleteReceiptById(int id);
}
