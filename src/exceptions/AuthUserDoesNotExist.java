package exceptions;

public class AuthUserDoesNotExist extends Exception{
    public AuthUserDoesNotExist(){
        super("Username is not correct");
    }
}
