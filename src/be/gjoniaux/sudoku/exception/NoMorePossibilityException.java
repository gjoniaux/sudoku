package be.gjoniaux.sudoku.exception;

public class NoMorePossibilityException extends BadChoiceException {
    public NoMorePossibilityException() {
        super();
    }

    public NoMorePossibilityException(String description) {
        super(description);
    }
}
