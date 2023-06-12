package odogwuHotels.dto.requests;

import lombok.Data;

@Data
public class ReceiptRequest {
    private String email;
    private int roomNumber;
}
