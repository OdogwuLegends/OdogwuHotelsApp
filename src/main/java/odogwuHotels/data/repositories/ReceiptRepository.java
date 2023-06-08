package odogwuHotels.data.repositories;

import odogwuHotels.data.models.Receipt;

import java.util.List;

public interface ReceiptRepository {
    Receipt saveReceipt(Receipt receipt);
    Receipt editReceipt(Receipt receipt);
    Receipt findById(int id);
    List<Receipt> findAllReceipts();
    void deleteReceiptById(int id);
}
