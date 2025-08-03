package io;

import lombok.Getter;
import lombok.Setter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
/**
 * @author zqw
 * @date 2020/03/06
 */
@Getter
class LineNumReader {
    @Setter
    private Reader r;
    private int lineNumber;

    public LineNumReader() {
    }

    public LineNumReader(Reader r) {
        super();
        this.r = r;
    }

    public String readLine() throws IOException {
        lineNumber++;
        return getString(r);
    }

    public String getString(Reader r) throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = r.read()) != -1) {
            if (ch == '\r') {
                continue;
            }
            if (ch == '\n') {
                return sb.toString();
            } else {
                sb.append((char) ch);
            }
        }
        if (!sb.isEmpty()) {
            return sb.toString();
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        LineNumReader lineNumReader = new LineNumReader(new FileReader("io/LineNumReader.java"));
        String s;
        while ((s = lineNumReader.readLine()) != null) {
            System.out.println(lineNumReader.getLineNumber() + ":" + s);
        }
    }
}
