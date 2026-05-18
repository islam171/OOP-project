package exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String msg){
        super("User not found");
    }

}
