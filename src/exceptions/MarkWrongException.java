package exceptions;

public class MarkWrongException extends Exception{

    public MarkWrongException(String msg){
        super("Mark wrong" + msg);
    }
}
