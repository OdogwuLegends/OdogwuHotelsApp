package odogwuHotels.dto.responses;

import lombok.Data;

@Data
public class FeedBackResponse {
    private String message;
    private String response;
    private int id;

    public String toString(){
        return "\n"+response+"\n";
    }
}
