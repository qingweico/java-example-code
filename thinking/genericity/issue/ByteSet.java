package thinking.genericity.issue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zqw
 * @date 2021/1/20
 */
class ByteSet {
    public static void main(String[] args) {
        Byte[] possible = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Set<Byte> mySet = new HashSet<>(Arrays.asList(possible));
        // But you can not do this
        // Set<Byte> mySet1 = new HashSet<>(Arrays.<Byte>asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        // 因为 Integer 不会转换为 Byte
        Set<Byte> mySet2 = new HashSet<>(Arrays.asList((byte) 1, (byte) 2, (byte) 3,
                (byte) 4, (byte) 5, (byte) 6,
                (byte) 7, (byte) 8));
        System.out.println(mySet);
        System.out.println(mySet2);
    }
}
