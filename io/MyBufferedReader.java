package io;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class MyBufferedReader {
    private final Reader r;

    public MyBufferedReader(Reader r) {
        this.r = r;
    }

    public String readLine() throws IOException {
        StringBuilder str = new StringBuilder();
        int ch;
        while ((ch = r.read()) != -1) {
            if (ch == '\r') {
                continue;
            }
            if (ch == '\n') {
                return str.toString();
            } else {
                str.append((char) ch);
            }
        }
        if (str.length() > 0) {
            return str.toString();
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "MyBufferedReader{" +
                "r=" + r +
                '}';
    }

    public static void main(String[] args) throws IOException {
        MyBufferedReader mbr = new MyBufferedReader(new FileReader("src/io/temp.txt"));
        String line;
        while ((line = mbr.readLine()) != null) {
            System.out.println(line);
        }
    }
}
