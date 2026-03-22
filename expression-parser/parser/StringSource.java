package expression.parser;

import java.util.InputMismatchException;

public class StringSource implements CharSource {
    private final String input;
    private int position;

    public StringSource(String input) {
        this.input = input;
    }

    @Override
    public boolean hasNext() {
        return position < input.length();
    }

    @Override
    public char next() {
        char c = input.charAt(position);
        position++;
        return c;
    }

    @Override
    public InputMismatchException error(String message) {
        return new InputMismatchException(position + ":" + message);
    }
}
