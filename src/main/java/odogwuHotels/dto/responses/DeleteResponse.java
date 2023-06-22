package odogwuHotels.dto.responses;

import lombok.Data;

@Data
public class DeleteResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String message;
    private int roomNumber;

    public String toString(){
        return "\n"+message+"\n";
    }
}
