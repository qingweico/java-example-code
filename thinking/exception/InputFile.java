package thinking.exception;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static util.Print.print;

/**
 * Paying attention to exception in constructors.
 *
 * @author:qiming
 * @date: 2021/1/18
 */
public class InputFile {
    private BufferedReader in;

    public InputFile(String fileName) throws Exception {
        try {
            in = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            print("Could not open " + fileName);
            throw e;
        } catch (Exception e) {
            try {
                in.close();
            } catch (IOException ex) {
                print("in.close() unsuccessful");
            }
            throw e;
        } finally {
            print("in.close()");
        }
    }

    public String getLine() {
        String s;
        try {
            s = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException("readLine() failed");
        }
        return s;
    }

    public void dispose() {
        try {
            in.close();
            print("dispose() successful");
        } catch (IOException ex) {
            throw new RuntimeException("in.close() failed");
        }
    }
}

