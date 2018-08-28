package be.gjoniaux.exception;

public class NoMorePossibilityException extends BadChoiceException {
    public NoMorePossibilityException() {
        super();
    }

    public NoMorePossibilityException(String description) {
        super(description);
    }
}
