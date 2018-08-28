package be.gjoniaux.exception;

public class BadGridException extends RuntimeException {
    public BadGridException() {
        super();
    }

    public BadGridException(String description) {
        super(description);
    }
}
