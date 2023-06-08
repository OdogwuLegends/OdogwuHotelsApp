//package odogwuHotels.services;
//
//import odogwuHotels.Map;
//import odogwuHotels.data.models.Admin;
//import odogwuHotels.data.models.Receipt;
//import odogwuHotels.data.models.Reservation;
//import odogwuHotels.dto.requests.ReceiptRequest;
//import odogwuHotels.dto.responses.ReceiptResponse;
//import odogwuHotels.dto.responses.ReservationResponse;
//
//public class OHPaymentService implements PaymentService{
//    private final ReservationService reservationService = new OHReservationService();
//
//    @Override
//    public void makePayment() {
//
//    }
//
//    @Override
//    public ReceiptResponse generateReceipt(ReceiptRequest request, Admin adminToApprove, Receipt receipt) {
//        ReservationResponse reservation = reservationService.findReservationByRoomNumber(Map.receiptReqToReservationReq(request));
//        Reservation foundReservation = Map.reservationResponseToReservation(reservation);
//        return Map.receiptRequestToResponse(foundReservation,adminToApprove,receipt);
//    }
//}
