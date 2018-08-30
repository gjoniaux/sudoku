package be.gjoniaux.sudoku.exception;

public class BadGridException extends RuntimeException {
    public BadGridException() {
        super();
    }

    public BadGridException(String description) {
        super(description);
    }
}
