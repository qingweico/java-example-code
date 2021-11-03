package io;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class BuffReader {
    private final Reader r;
    private final LineNumReader myLineNumberReader = new LineNumReader();

    public BuffReader(Reader r) {
        this.r = r;
    }

    public String readLine() throws IOException {
       return myLineNumberReader.getString(r);
    }

    @Override
    public String toString() {
        return "BuffReader{" +
                "r=" + r +
                '}';
    }

    public static void main(String[] args) throws IOException {
        BuffReader mr = new BuffReader(new FileReader("io/BuffReader.java"));
        String line;
        while ((line = mr.readLine()) != null) {
            System.out.println(line);
        }
    }
}
