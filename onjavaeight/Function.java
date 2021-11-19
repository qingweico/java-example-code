package onjavaeight;

/**
 * @author:qiming
 * @date: 2021/1/22
 */
public class Function {
    public static void startThread(Runnable r){
        new Thread(r).start();
    }

    public static void main(String[] args) {
        startThread( () -> System.out.println("thread started..."));
    }
}
