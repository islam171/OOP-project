package exceptions;

public class NewsException extends Exception{

    public NewsException(String msg){
        super("News exception: " + msg);
    }
}
