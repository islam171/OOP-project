package exceptions;

public class AuthException extends Exception{
    public AuthException(String message){
        super("Auth exception: " + message);
    }
}
