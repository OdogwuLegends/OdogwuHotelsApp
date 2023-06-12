package odogwuHotels.services;

import odogwuHotels.dto.requests.ReservationRequest;
import java.math.BigDecimal;

public class OHPaymentService implements PaymentService{
    @Override
    public BigDecimal makePayment(ReservationRequest request) {
        return request.getMakePayment();
    }
}
