package thinking.concurrency.atom;

/**
 * @author:周庆伟
 * @date: 2021/1/19
 */
public class SerialNumberGenerator {
    public static volatile int serialNumber = 0;
    public static int nextSerialNumber(){
        // Not thread-safe
        return serialNumber++;
    }

}
