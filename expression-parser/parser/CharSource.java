package expression.parser;

import java.util.InputMismatchException;

public interface CharSource {
    boolean hasNext();

    char next();

    InputMismatchException error(String message);
}
