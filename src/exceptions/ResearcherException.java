package exceptions;

public class ResearcherException extends Exception {
    public ResearcherException(String message) {
        super("Researcher exception: " + message);
    }
}
