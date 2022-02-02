package io;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author zqw
 * @date 2020/03/05
 */
public class BuffReader {
    private final Reader r;
    private final LineNumReader lineNumReader = new LineNumReader();

    public BuffReader(Reader r) {
        this.r = r;
    }

    public String readLine() throws IOException {
       return lineNumReader.getString(r);
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
