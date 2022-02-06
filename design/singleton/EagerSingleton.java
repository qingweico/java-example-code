package design.singleton;

/**
 * @author zqw
 * @date 2021/12/21
 */
public class EagerSingleton {

    private static long id;

    @Override
    public String toString() {
        return "[ EagerSingleton id = " + id + " ]";
    }

    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {
        id++;
    }

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {

    }
}
