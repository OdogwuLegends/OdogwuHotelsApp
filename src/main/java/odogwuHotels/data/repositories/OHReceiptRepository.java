package odogwuHotels.data.repositories;

import odogwuHotels.Utils;
import odogwuHotels.data.models.Receipt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OHReceiptRepository implements ReceiptRepository{
    List<Receipt> receipts = new ArrayList<>();

    @Override
    public Receipt saveReceipt(Receipt receipt) {
        receipt.setId(Utils.generateId());
        receipts.add(receipt);
        return null;
    }

    @Override
    public Receipt editReceipt(Receipt receipt) {
        Receipt foundReceipt = findById(receipt.getId());

        if(foundReceipt != null){
            if(receipt.getFirstName() != null) foundReceipt.setFirstName(receipt.getFirstName());
            if(receipt.getLastName() != null) foundReceipt.setLastName(receipt.getLastName());
            if(receipt.getBalance() != null) foundReceipt.setBalance(receipt.getBalance());
            if(receipt.getApprovedBy() != null) foundReceipt.setApprovedBy(receipt.getApprovedBy());
            if(receipt.getAmountPaid() != null) foundReceipt.setAmountPaid(receipt.getAmountPaid());
            if(receipt.getRoomType() != null) foundReceipt.setRoomType(receipt.getRoomType());
            if(receipt.getRoomPrice() != null) foundReceipt.setRoomPrice(receipt.getRoomPrice());
            if(receipt.getDurationOfStay() != null) foundReceipt.setDurationOfStay(receipt.getDurationOfStay());
            if(receipt.getCheckInDate() != null) foundReceipt.setCheckInDate(receipt.getCheckInDate());
            if(receipt.getCheckOutDate() != null) foundReceipt.setCheckOutDate(receipt.getCheckOutDate());
            if(receipt.isFullyPaidFor() != foundReceipt.isFullyPaidFor()) foundReceipt.setFullyPaidFor(receipt.isFullyPaidFor());
        }
        return foundReceipt;
    }

    @Override
    public Receipt findById(int id) {
        for(Receipt foundReceipt : receipts){
            if(Objects.equals(foundReceipt.getId(),id)) return foundReceipt;
        }
        return null;
    }

    @Override
    public List<Receipt> findAllReceipts() {
        return receipts;
    }

    @Override
    public void deleteReceiptById(int id) {
        Receipt foundReceipt = findById(id);
        if(foundReceipt != null) receipts.remove(foundReceipt);
    }
}
