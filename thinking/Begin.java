package thinking;

/**
 * @author:qiming
 * @date: 2020/09/12
 */
public class Begin {
    public static void main(String[] args) {
        System.getProperties().list(System.out);
        System.out.println();
        System.out.println(System.getProperty("user.name"));
        System.out.println();
        System.out.println(System.getProperty("java.library.path"));
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println(System.getProperty("user.dir"));
    }
}

