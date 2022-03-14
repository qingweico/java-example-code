package collection.set;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zqw
 * @date 2022/3/14
 */
public class BitSetExample {
    private static final Integer MOBILE_PREFIX = 135;
    private static final Integer MOBILE_SUFFIX = 24859651;
    public static void main(String[] args) {
        Map<Integer, BitSet> bitMap = new HashMap<>(1);
        bitMap.computeIfAbsent(MOBILE_PREFIX, k -> new BitSet()).set(MOBILE_SUFFIX);
        System.out.println(bitMap.get(MOBILE_PREFIX));
    }
}
