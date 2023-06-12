package odogwuHotels.services;

import odogwuHotels.data.models.Admin;
import odogwuHotels.data.models.Receipt;
import odogwuHotels.dto.requests.ReceiptRequest;
import odogwuHotels.dto.requests.ReservationRequest;
import odogwuHotels.dto.responses.ReceiptResponse;

import java.math.BigDecimal;

public interface PaymentService {
    BigDecimal makePayment(ReservationRequest request);
}
