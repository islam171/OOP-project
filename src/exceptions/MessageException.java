package exceptions;

public class MessageException extends Exception {
    public MessageException(String message) {
        super("Message exception " + message);
    }
}
