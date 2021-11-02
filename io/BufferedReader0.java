package io;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class BufferedReader0 {
    private final Reader r;
    private final LineNumReader myLineNumberReader = new LineNumReader();

    public BufferedReader0(Reader r) {
        this.r = r;
    }

    public String readLine() throws IOException {
       return myLineNumberReader.getString(r);
    }

    @Override
    public String toString() {
        return "MyBufferedReader{" +
                "r=" + r +
                '}';
    }

    public static void main(String[] args) throws IOException {
        BufferedReader0 mbr = new BufferedReader0(new FileReader("io/MyBufferedReader.java"));
        String line;
        while ((line = mbr.readLine()) != null) {
            System.out.println(line);
        }
    }
}
