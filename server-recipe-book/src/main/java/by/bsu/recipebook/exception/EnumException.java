package by.bsu.recipebook.exception;

public class EnumException extends Exception {
    public EnumException() {
        super();
    }

    public EnumException(String message) {
        super(message);
    }

    public EnumException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnumException(Throwable cause) {
        super(cause);
    }
}
