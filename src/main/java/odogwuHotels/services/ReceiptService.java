package odogwuHotels.services;

import odogwuHotels.data.models.Admin;
import odogwuHotels.data.models.Receipt;
import odogwuHotels.dto.requests.ReservationRequest;
import odogwuHotels.dto.responses.DeleteResponse;
import odogwuHotels.dto.responses.ReceiptResponse;
import odogwuHotels.dto.responses.UpdateResponse;
import odogwuHotels.exceptions.AdminException;
import odogwuHotels.exceptions.EntityNotFoundException;

import java.util.List;

public interface ReceiptService {
    ReceiptResponse createReceipt(ReservationRequest request, Admin admin) throws AdminException;
    ReceiptResponse findReceiptById(int id) throws EntityNotFoundException;
    ReceiptResponse findReceiptByEmail(String email) throws EntityNotFoundException;
    ReceiptResponse generateReceiptById(int id) throws EntityNotFoundException;
    ReceiptResponse generateReceiptByEmail(String email) throws EntityNotFoundException;
    List<Receipt> findAllReceipts();
    DeleteResponse deleteReceiptById(int id) throws EntityNotFoundException;
}
