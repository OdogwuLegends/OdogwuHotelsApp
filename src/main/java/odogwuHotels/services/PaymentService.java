package odogwuHotels.services;

import odogwuHotels.data.models.Admin;
import odogwuHotels.data.models.Receipt;
import odogwuHotels.dto.requests.ReceiptRequest;
import odogwuHotels.dto.responses.ReceiptResponse;

public interface PaymentService {
    void makePayment();
    ReceiptResponse generateReceipt(ReceiptRequest request, Admin adminToApprove, Receipt receipt);
}
