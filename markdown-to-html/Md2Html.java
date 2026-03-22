package md2html;

import markup.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;


record Refund(String finalStr, int indexEnd, boolean closeOrNot) {
}

public class Md2Html {

    public static void main(String[] args) {

        StringBuilder changingSb = new StringBuilder();
        try (
                MyScanner scan = new MyScanner(new FileInputStream(args[0]));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
            while (scan.hasNextLine()) {
                String scanStr = scan.nextLine();
                if (!scanStr.isEmpty()) {
                    changingSb.append(scanStr).append(System.lineSeparator());
                } else if (!changingSb.isEmpty()) {
                    extracted(changingSb, writer);
                }
            }
            extracted(changingSb, writer);
        } catch (FileNotFoundException e) {
            System.out.println("Problem with file:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Failed to write in file:" + e.getMessage());
        }
    }

    private static void extracted(StringBuilder changingSb, BufferedWriter writer) throws IOException {
        Refund res = push(changingSb.toString(), 0, null);
        changingSb.setLength(0);
        changingSb.append(res.finalStr());
        topWrapper(changingSb);

        writer.write(changingSb.toString());
        writer.newLine();
        changingSb.setLength(0);
    }


    private static void topWrapper(StringBuilder sb) {
        if (sb.isEmpty()) {
            return;
        }
        int countGrid = 0;
        HeaderParagraph text = null;
        sb.setLength(sb.length() - System.lineSeparator().length());
        if (sb.charAt(0) == '#') {
            countGrid++;
            for (int i = 1; i < sb.length() && sb.charAt(i) == '#'; i++) {
                countGrid++;
            }
            if (countGrid < sb.length()) {
                if (sb.charAt(countGrid) == ' ') {
                    text = new Header(List.of(new Text(sb.substring(countGrid + 1))), countGrid);
                } else {
                    text = new Paragraph(List.of(new Text(sb.toString())));
                }
            }
        } else {
            text = new Paragraph(List.of(new Text(sb.toString())));
        }
        StringBuilder sbHtml = new StringBuilder();
        text.toHtml(sbHtml);
        sb.setLength(0);
        sb.append(sbHtml);
    }


    private static int indexEnd(StringBuilder sbToHtml, HTML markdownToHtml, Refund resTransmitted ){
        StringBuilder stringBuilder = new StringBuilder();
        markdownToHtml.toHtml(stringBuilder);
        sbToHtml.append(stringBuilder);
        return resTransmitted.indexEnd();
    }

    //all without paragraph and header
    private static Refund push(String str, int startIndex, String symStop) {
        String stringChange = str; // :NOTE: нейминг
        StringBuilder sbHtml = new StringBuilder();
        for (int i = startIndex; i < stringChange.length(); i++) {
            char sym = stringChange.charAt(i);
            if (symStop != null) {
                if (symStop.length() == 1 && symStop.charAt(0) == sym && !stringChange.startsWith(symStop + symStop, i)) {
                    return new Refund(sbHtml.toString(), i, true);
                } else if (symStop.length() == 2 && stringChange.startsWith(symStop, i)) {
                    return new Refund(sbHtml.toString(), i + 1, true);
                }
            }

            // :NOTE: copy-paste
            switch (sym) {
                case ('<') -> sbHtml.append("&lt;");
                case ('>') -> sbHtml.append("&gt;");
                case ('&') -> sbHtml.append("&amp;");
                case ('\\') -> {
                    if (i + 1 >= stringChange.length()) {
                        sbHtml.append(sym);
                    } else {
                        if (stringChange.charAt(i + 1) == ' ') {
                            sbHtml.append(sym);
                        } else {
                            sbHtml.append(stringChange.charAt(i + 1));
                            i++;
                        }
                    }
                }
                case ('`') -> {
                    Refund res = push(stringChange, i + 1, "" + sym);
                    if (res.closeOrNot()) {
                        i = indexEnd(sbHtml, new Code(List.of(new Text(res.finalStr()))), res);
                    } else {
                        sbHtml.append(sym);
                    }
                }
                case ('-') -> {
                    if (i + 1 < stringChange.length() && stringChange.charAt(i + 1) == sym) {
                        Refund res = push(stringChange, i + 2, "" + sym + sym);
                        if (res.closeOrNot()) {
                            i = indexEnd(sbHtml, new Strikeout(List.of(new Text(res.finalStr()))), res);
                        } else {
                            sbHtml.append(sym);
                        }
                    } else {
                        sbHtml.append(sym);
                    }
                }
                case ('!') -> {
                    if (i + 1 < stringChange.length() && stringChange.charAt(i + 1) == sym) {
                        Refund res = push(stringChange, i + 2, "" + sym + sym);
                        if (res.closeOrNot()) {
                            i = indexEnd(sbHtml, new Samp(List.of(new Text(res.finalStr()))), res);
                        } else {
                            sbHtml.append(sym);
                        }
                    } else {
                        sbHtml.append(sym);
                    }
                }
                case ('_'), ('*') -> {
                    if (i + 1 < stringChange.length() && stringChange.charAt(i + 1) == sym) {
                        Refund res = push(stringChange, i + 2, "" + sym + sym);
                        if (res.closeOrNot()) {
                            i = indexEnd(sbHtml, new Strong(List.of(new Text(res.finalStr()))), res);
                        } else {
                            sbHtml.append(sym);
                        }
                    } else {
                        Refund res = push(stringChange, i + 1, "" + sym);
                        if (res.closeOrNot()) {
                            i = indexEnd(sbHtml, new Emphasis(List.of(new Text(res.finalStr()))), res);
                        } else {
                            sbHtml.append(sym);
                        }
                    }
                }
                default -> sbHtml.append(sym);
            }
        }
        stringChange = sbHtml.toString();
        return new Refund(stringChange, 0, false);
    }
}
