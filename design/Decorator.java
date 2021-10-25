package design;


/**
 * Decorator mode
 *
 * @author:qiming
 * @date: 2020/11/5
 */
abstract class Reader {
    public abstract void close();
}

class FileReader extends Reader {

    @Override
    public void close() {
        System.out.println("FileReader`s close is running...");

    }
}

class BufferedReader extends Reader {
    Reader reader;

    public BufferedReader(Reader reader) {
        this.reader = reader;
    }

    @Override
    public void close() {
        reader.close();
        System.out.println("BufferedReader`s close is running...");

    }
}
public class Decorator {
    public static void main(String[] args) {
        Reader reader = new FileReader();
        BufferedReader br = new BufferedReader(reader);
        br.close();
    }
}
