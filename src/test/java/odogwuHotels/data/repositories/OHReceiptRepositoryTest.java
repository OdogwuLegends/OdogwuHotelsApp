package odogwuHotels.data.repositories;

import odogwuHotels.Utils;
import odogwuHotels.data.models.Admin;
import odogwuHotels.data.models.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static odogwuHotels.data.models.RoomType.DOUBLE;
import static odogwuHotels.data.models.RoomType.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

class OHReceiptRepositoryTest {
    private final ReceiptRepository receiptRepository = new OHReceiptRepository();

    private Receipt firstReceiptSaved;
    private Receipt secondReceiptSaved;

    @BeforeEach
    void setUp(){
        firstReceiptSaved = firstReceipt();
        receiptRepository.saveReceipt(firstReceiptSaved);
        secondReceiptSaved = secondReceipt();
        receiptRepository.saveReceipt(secondReceiptSaved);
    }

    @Test
    void saveReceipt(){
        assertNotNull(firstReceiptSaved);
        assertNotNull(secondReceiptSaved);

        List<Receipt> allReceipts = receiptRepository.findAllReceipts();
        assertEquals(2,allReceipts.size());
        assertTrue(receiptRepository.findAllReceipts().contains(firstReceiptSaved));
        assertTrue(allReceipts.contains(firstReceiptSaved));
    }
    @Test
    void editReceipt(){
        Receipt foundReceipt = receiptRepository.findById(firstReceiptSaved.getId());
        LocalDate checkIn = Utils.stringToLocalDate("11/06/2023");
        assertEquals(checkIn,foundReceipt.getCheckInDate());
        LocalDate checkOut = Utils.stringToLocalDate("16/06/2023");
        assertEquals(checkOut,foundReceipt.getCheckOutDate());

        LocalDate newCheckIn = Utils.stringToLocalDate("20/06/2023");
        LocalDate newCheckOut = Utils.stringToLocalDate("22/06/2023");
        foundReceipt.setCheckInDate(newCheckIn);
        foundReceipt.setCheckOutDate(newCheckOut);
        long duration = ChronoUnit.DAYS.between(checkOut,newCheckIn);
        foundReceipt.setDurationOfStay(Utils.durationOfStay(duration));

        receiptRepository.editReceipt(foundReceipt);

        assertSame(firstReceiptSaved,foundReceipt);
        assertEquals(firstReceiptSaved.getDurationOfStay(),foundReceipt.getDurationOfStay());
    }

    @Test
    void findReceiptById(){
        Receipt foundReceipt = receiptRepository.findById(firstReceiptSaved.getId());
        assertSame(firstReceiptSaved,foundReceipt);
        assertEquals(firstReceiptSaved.getCheckInDate(),foundReceipt.getCheckInDate());
    }

    @Test
    void findAllReceipts(){
        List<Receipt> allReceipts = receiptRepository.findAllReceipts();
        assertEquals(2,allReceipts.size());
        assertTrue(allReceipts.contains(firstReceiptSaved));
        assertTrue(allReceipts.contains(secondReceiptSaved));
    }
    @Test
    void deleteReceiptById(){
        List<Receipt> allReceipts = receiptRepository.findAllReceipts();
        assertEquals(2,allReceipts.size());

        receiptRepository.deleteReceiptById(firstReceiptSaved.getId());
        assertEquals(1,allReceipts.size());

        receiptRepository.deleteReceiptById(secondReceiptSaved.getId());
        assertEquals(0,allReceipts.size());

        assertFalse(allReceipts.contains(firstReceiptSaved));
        assertFalse(allReceipts.contains(secondReceiptSaved));
    }






    private Receipt firstReceipt(){
        Receipt receipt = new Receipt();

        receipt.setFirstName("Eden");
        receipt.setLastName("Hazard");
        receipt.setId(Utils.generateId());
        receipt.setRoomType(DOUBLE);
        receipt.setRoomPrice(BigDecimal.valueOf(100));
        receipt.setBalance(BigDecimal.ZERO);
        receipt.setFullyPaidFor(true);
        receipt.setAmountPaid(BigDecimal.valueOf(100));
        Admin admin = new Admin();
        admin.setFirstName("Bruce");
        admin.setLastName("Lee");
        receipt.setApprovedBy(admin.getFirstName()+" "+admin.getLastName());

        LocalDate checkInDate = Utils.stringToLocalDate("11/06/2023");
        LocalDate checkOutDate = Utils.stringToLocalDate("16/06/2023");
        receipt.setCheckInDate(checkInDate);
        receipt.setCheckOutDate(checkOutDate);
        long duration = ChronoUnit.DAYS.between(checkOutDate,checkInDate);
        receipt.setDurationOfStay(Utils.durationOfStay(duration));

        return receipt;
    }

    private Receipt secondReceipt(){
        Receipt receipt = new Receipt();

        receipt.setFirstName("Michael");
        receipt.setLastName("Hemming");
        receipt.setId(Utils.generateId());
        receipt.setRoomType(SINGLE);
        receipt.setRoomPrice(BigDecimal.valueOf(50));
        receipt.setBalance(BigDecimal.valueOf(50));
        receipt.setFullyPaidFor(false);
        receipt.setAmountPaid(BigDecimal.valueOf(50));
        Admin admin = new Admin();
        admin.setFirstName("Jon");
        admin.setLastName("Snow");
        receipt.setApprovedBy(admin.getFirstName()+" "+admin.getLastName());

        LocalDate checkInDate = Utils.stringToLocalDate("06/06/2023");
        LocalDate checkOutDate = Utils.stringToLocalDate("10/06/2023");
        receipt.setCheckInDate(checkInDate);
        receipt.setCheckOutDate(checkOutDate);
        long duration = ChronoUnit.DAYS.between(checkOutDate,checkInDate);
        receipt.setDurationOfStay(Utils.durationOfStay(duration));

        return receipt;
    }


}