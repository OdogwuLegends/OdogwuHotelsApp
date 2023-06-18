package odogwuHotels.data.repositories;

import odogwuHotels.myUtils.Utils;
import odogwuHotels.data.models.Receipt;

import java.util.*;

public class OHReceiptRepository implements ReceiptRepository{
    static List<Receipt> receiptList = new ArrayList<>();

    private static OHReceiptRepository singleObject;

    private OHReceiptRepository(){

    }
    public static OHReceiptRepository createObject(){
        if(singleObject == null){
            singleObject = new OHReceiptRepository();
        }
        return singleObject;
    }

    @Override
    public Receipt saveReceipt(Receipt receipt) {
        receipt.setId(Utils.generateId());
        receiptList.add(receipt);
        return receipt;
    }

    @Override
    public int getIndex(Receipt receiptToCheck){
        int index = 0;
        for (Receipt receipt : receiptList){
            if(Objects.equals(receipt,receiptToCheck))
                index = receiptList.indexOf(receipt);
        }
        return index;
    }
    @Override
    public Map<Integer, Receipt> getIdsOfAllCustomers(){
        Map<Integer, Receipt> idOfReceipts = new TreeMap<>();
        for(Receipt receipt : receiptList){
            idOfReceipts.put(receipt.getId(),receipt);
        }
        return idOfReceipts;
    }

    @Override
    public Receipt findById(int id) {
        for(Receipt foundReceipt : receiptList){
            if(Objects.equals(foundReceipt.getId(),id)) return foundReceipt;
        }
        return null;
    }

    @Override
    public Receipt findByEmail(String email) {
        for(Receipt foundReceipt : receiptList){
            if(Objects.equals(foundReceipt.getEmail(),email)) return foundReceipt;
        }
        return null;
    }

    @Override
    public List<Receipt> findAllReceipts() {
        return receiptList;
    }

    @Override
    public void deleteReceiptById(int id) {
        Receipt foundReceipt = findById(id);
        if(foundReceipt != null) receiptList.remove(foundReceipt);
    }
}
