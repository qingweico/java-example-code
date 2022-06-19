package thinking.exception;

/**
 * Exception loss
 *
 * @author:qiming
 * @date: 2020/11/22
 */
class VeryImportantException extends Exception {
    public String toString() {
        return "A very important exception!";
    }
}

class HoHumException extends Exception {
    public String toString() {
        return "A trivial exception";
    }
}

public class LostMessage {
    void f() throws VeryImportantException {
        throw new VeryImportantException();
    }

    void dispose() throws HoHumException {
        throw new HoHumException();
    }

    public static void main(String[] args) {
        try {
            LostMessage lm = new LostMessage();
            try {
                lm.f();  // the exception will be covered
            } finally {
                lm.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

}

// This is an easier way to lose exceptions.
class ExceptionsSilencer {
    public static void main(String[] args) {
        try {
            throw new RuntimeException();
        } finally {
            return;
        }
    }
}
