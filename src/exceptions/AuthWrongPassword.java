package exceptions;

public class AuthWrongPassword extends Exception {
    public AuthWrongPassword(){
        super("Password is not correct");
    }
}
