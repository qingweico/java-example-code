package thinking.exception;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author zqw
 * @date 2021/2/7
 */
class MainException {

    // Pass all exception to the console:

    public static void main(String[] args) throws IOException {
        // Open the file
        FileInputStream is =
                new FileInputStream("exception/MainException.java");
        // Use the file
        // Close the file
        is.close();

    }

}
