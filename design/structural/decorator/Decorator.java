package design.structural.decorator;


/**
 * Decorator mode: 装饰器模式允许在不修改原类的情况下, 给对象增加新的功能
 *
 * @author zqw
 * @date 2020/11/5
 */
public class Decorator {
    public static void main(String[] args) {
        AbstractReader reader = new FileReader();
        BufferedReader br = new BufferedReader(reader);
        br.close();
    }
}

abstract class AbstractReader {
    /**
     * {@link java.io.Reader#close()}
     */
    public abstract void close();
}

class FileReader extends AbstractReader {

    @Override
    public void close() {
        System.out.println("FileReader`s close is running...");

    }
}

class BufferedReader extends AbstractReader {
    AbstractReader reader;

    public BufferedReader(AbstractReader reader) {
        this.reader = reader;
    }

    @Override
    public void close() {
        reader.close();
        System.out.println("BufferedReader`s close is running...");

    }
}

