package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class MyScanner implements AutoCloseable {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final int BUFFER_SIZE = 1;
    private int pointer = 0;
    private int pointerLineSep = 0;

    public int pointerString = 1;

    private final Reader reader;
    private final char[] buffer = new char[BUFFER_SIZE];

    private int readChars = 0;

    public MyScanner(InputStream inputStream) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public MyScanner(File fileName) throws FileNotFoundException {
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
    }

    public MyScanner(String source) {                                        // конструктор для строки
        this.reader = new BufferedReader(new StringReader(source));
    }


    public void fillBuffer() throws IOException {
        this.readChars = reader.read(buffer);
        pointer = 0;
    }

    public int getPointerString() {
        return pointerString;
    }

    public String nextPredict(Predicate<Character> pred) throws IOException {
        StringBuilder token = new StringBuilder();
        while (readChars != -1) {
            if (pointer >= readChars) {
                fillBuffer();
                if (readChars == -1) {
                    return token.toString();
                }
            }
            char sym = buffer[pointer++];
            isThisString(sym);
            if (pred.test(sym)) {
                if (!token.isEmpty()) {
                    return token.toString();
                }
            } else token.append(sym);
        }
        return token.toString();
    }


    private void isThisString(char sym) {
        int allLenLineSep = LINE_SEPARATOR.length();
        if (LINE_SEPARATOR.charAt(pointerLineSep) == sym) {
            pointerLineSep++;
            if (allLenLineSep == pointerLineSep) {
                pointerLineSep = 0;
                pointerString++;
            }
        } else {
            pointerLineSep = 0;
        }
    }

    public boolean hasNextPred(Predicate<Character> pred) throws IOException {
        while (pointer >= readChars) {
            fillBuffer();
            if (readChars == -1) return false;
        }
        while (pointer < readChars) {
            char sym = buffer[pointer];
            if (pred.test(sym)) {
                return true;
            } else {
                pointer++;
                isThisString(sym);
                if (pointer >= readChars) {
                    fillBuffer();
                    if (readChars == -1) return false;
                }
            }
        }
        return false;
    }

    public String next() throws IOException {
        StringBuilder token = new StringBuilder();
        int lenLinSep = 0;
        while (readChars != -1) {
            if (pointer >= readChars) {
                fillBuffer();
                if (readChars == -1) {
                    return token.toString();
                }
            }
            char sym = buffer[pointer++];
            if (sym == LINE_SEPARATOR.charAt(lenLinSep)) {
                lenLinSep++;
                if (lenLinSep == LINE_SEPARATOR.length()) {
                    return token.toString();
                }
            } else {
                if (lenLinSep > 0) {
                    token.append(LINE_SEPARATOR, 0, lenLinSep);
                    lenLinSep = 0;
                } else {
                    token.append(sym);
                }
            }
        }
        return token.toString();
    }

    public int nextInt() throws IOException {
        String strTest = next();
        int res;
        if (strTest == null) {
            throw new NullPointerException();
        }
        if (strTest.isEmpty()) {
            throw new NoSuchElementException();
        }
        try {
            res = Integer.parseInt(strTest);
        } catch (NumberFormatException e) {
            throw new InputMismatchException();
        }
        return res;
    }


    public String nextLine() throws IOException {
        StringBuilder token = new StringBuilder();
        int lenLinSep = 0;
        while (readChars != -1) {
            if (pointer >= readChars) {
                fillBuffer();
                if (readChars == -1) {
                    return token.toString();

                }
            }
            char sym = buffer[pointer++];
            if (sym == LINE_SEPARATOR.charAt(lenLinSep)) {
                lenLinSep++;
                if (lenLinSep == LINE_SEPARATOR.length()) {
                    return token.toString();
                }
            } else {
                if (lenLinSep > 0) {
                    token.append(LINE_SEPARATOR, 0, lenLinSep);
                    lenLinSep = 0;
                } else {
                    token.append(sym);
                }
            }
        }
        return token.toString();
    }


    public boolean hasNext() throws IOException {
        while (pointer >= readChars) {
            fillBuffer();
            if (readChars == -1) return false;
        }
        while (pointer < readChars) {
            if (!Character.isWhitespace(buffer[pointer])) return true;
            else pointer++;

            if (pointer >= readChars) {
                fillBuffer();
                if (readChars == -1) return false;
            }
        }
        return false;
    }

    public boolean hasNextLine() throws IOException {
        if (pointer < readChars) {
            return true;
        }
        fillBuffer();
        if (readChars == -1) return false;
        return pointer < readChars;

    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}






