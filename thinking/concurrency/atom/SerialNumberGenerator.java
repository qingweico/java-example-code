package thinking.concurrency.atom;

import annotation.NotThreadSafe;

/**
 * @author:qiming
 * @date: 2021/1/19
 */
@NotThreadSafe
public class SerialNumberGenerator {
    public static volatile int serialNumber = 0;
    public static int nextSerialNumber(){
        // Not thread-safe
        return serialNumber++;
    }

}
