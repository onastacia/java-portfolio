package expression.parser;

import expression.exceptions.ParsingException;

public class BaseParser {
    private final CharSource source;
    private char ch;
    protected static final char EOF = 0;

    public BaseParser(CharSource source) {
        this.source = source;
        next();
    }

    protected void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            next();
        }
    }

    private void next() {
        ch = source.hasNext() ? source.next() : EOF;
    }

    protected boolean test(char c) { // :NOTE: не понятно что делает функция по названию
        return ch == c;
    }

    protected boolean between(char from, char to) { // :NOTE: isBetweenCh ?
        return from <= ch && ch <= to;
    }

    protected char take() {
        char current = ch;
        next();
        return current;
    }

    private boolean take(char c) {
        if (test(c)) {
            next();
            return true;
        }
        return false;
    }

    protected void expect(char c) {
        if (!take(c)) {
            throw new ParsingException("Excpected " + formateChar(c) + "  but got " + formateChar(ch) + "!");
        }
    }

    private String formateChar(char c) {
        return c == EOF ? "EOF" : "'" + c + "'";
    }

    protected void expect(String chars) {
        for (char c : chars.toCharArray()) {
            expect(c);
        }
    }

    protected RuntimeException error(String message) {
        return new ParsingException(message);
    }

    protected void expectEOF() {
        expect(EOF);
        if (ch == EOF) {
            return;
        }
        throw error("Expected EOF but got '" + ch + "'");
    }

}
