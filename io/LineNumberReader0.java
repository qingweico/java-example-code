package io;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class LineNumberReader0 {
    private Reader r;
    private int lineNumber;

    public LineNumberReader0() {
    }

    public LineNumberReader0(Reader r) {
        super();
        this.r = r;
    }

    public Reader getR() {
        return r;
    }

    public void setR(Reader r) {
        this.r = r;
    }

    public int getLineNumber() {
        return lineNumber;
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
        if (sb.length() > 0) {
            return sb.toString();
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        LineNumberReader0 myLineNumberReader = new LineNumberReader0(new FileReader("io/MyLineNumberReader.java"));
        String s;
        while ((s = myLineNumberReader.readLine()) != null) {
            System.out.println(myLineNumberReader.getLineNumber() + ":" + s);
        }


    }
}
