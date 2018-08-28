package be.gjoniaux.exception;

public class BadChoiceException extends RuntimeException {
    public BadChoiceException() {
        super();
    }

    public BadChoiceException(String description) {
        super(description);
    }
}
