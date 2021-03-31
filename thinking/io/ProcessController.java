package thinking.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author:÷‹«ÏŒ∞
 * @date: 2021/1/7
 */


public class ProcessController {
    final static String CLOSE = "CMD /C";

    public static void command(String command) {
        boolean err = false;
        try {
            Process process = new ProcessBuilder(command.split(" ")).start();
            BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while ((s = results.readLine()) != null) {
                System.out.println(s);
            }
            //TODO
            for (BufferedReader errors = new BufferedReader(new InputStreamReader(
                    process.getErrorStream())); (s = errors.readLine()) != null; err = true) {
                System.err.println(s);
            }
        } catch (IOException e) {
            if (command.startsWith(CLOSE)) {
                throw new RuntimeException(e);
            }
            command(CLOSE + command);
        }
        if (err) {
            throw new OsExecuteException("Errors executing " + command);
        }
    }

    public static void main(String[] args) {
        // Terminal:
        // javac ProcessController.java
        // cd ../../
        // java thinking.io.ProcessController
        //command("javap thinking.io.ProcessController");
        command("javap bin/thinking/io/ProcessController");

    }
}

class OsExecuteException extends RuntimeException {
    public OsExecuteException(String why) {
        super(why);
    }
}
