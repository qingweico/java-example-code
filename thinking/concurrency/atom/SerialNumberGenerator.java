package thinking.concurrency.atom;

import annotation.NotThreadSafe;

/**
 * @author zqw
 * @date 2021/1/19
 */
@NotThreadSafe
public class SerialNumberGenerator {
    public static int serialNumber = 0;
    public static int nextSerialNumber(){
        // Not thread-safe
        return serialNumber++;
    }

}
