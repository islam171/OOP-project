package exceptions;

public class SupervisorException extends RuntimeException {
    public SupervisorException(String message) {
        super("Supervisor error: " + message);
    }
}
