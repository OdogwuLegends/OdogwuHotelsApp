package odogwuHotels.data.repositories;

import odogwuHotels.data.models.Receipt;

import java.util.List;
import java.util.Map;

public interface ReceiptRepository {
    Receipt saveReceipt(Receipt receipt);
    Receipt updateReceipt (Receipt receipt);
    int getIndex(Receipt receiptToCheck);
    Map<Integer, Receipt> getIdsOfAllCustomers();
    Receipt findById(int id);
    Receipt findByEmail(String email);
    List<Receipt> findAllReceipts();
    void deleteReceiptById(int id);
}
