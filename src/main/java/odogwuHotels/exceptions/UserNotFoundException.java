package odogwuHotels.exceptions;

import lombok.Data;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String message){
        super(message);
    }
}
