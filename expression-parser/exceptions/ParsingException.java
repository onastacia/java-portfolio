package expression.exceptions;

// :NOTE: нужно проверяемое исключение
public class ParsingException extends RuntimeException {
    public ParsingException(String message) {
        super(message);
    }
}
