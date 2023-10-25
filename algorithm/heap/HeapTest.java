package algorithm.heap;

import util.RandomDataUtil;
import util.Tools;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * @author zqw
 * @date 2022/2/12
 */
public class HeapTest {
    @Test
    public void testHeap() {
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        int size = 100_000;
        int exclusive = 100_0000;
        for (int i = 0; i < size; i++) {
            maxHeap.add(RandomDataUtil.ri(exclusive));
        }
        System.out.println(maxHeap.size());
        ArrayList<Integer> list = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            list.add(maxHeap.extractMax());
        }
        Tools.assertSorted(list, true);
    }
}
