package io;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class MyLineNumberReader {
    private Reader r;
    private int lineNumber;

    public MyLineNumberReader(Reader r) {
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

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String readLine() throws IOException {
        lineNumber++;
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
        MyLineNumberReader myLineNumberReader = new MyLineNumberReader(new FileReader("src/io/temp"));
        String s;
        while ((s = myLineNumberReader.readLine()) != null) {
            System.out.println(myLineNumberReader.getLineNumber() + ":" + s);
        }


    }
}
