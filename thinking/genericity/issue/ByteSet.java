package thinking.genericity.issue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author:周庆伟
 * @date: 2021/1/20
 */
public class ByteSet {
    Byte[] possible = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    Set<Byte> mySet = new HashSet<>(Arrays.asList(possible));
    // But you can not do this
    // Set<Byte> mySet1 = new HashSet<>(Arrays.<Byte>asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
}
