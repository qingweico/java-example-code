package algorithm.heap;

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
        int count = 100_000;
        for (int i = 0; i < count; i++) {
            maxHeap.add((int) (Math.random() * 1000000));
        }
        System.out.println(maxHeap.size());
        ArrayList<Integer> list = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            list.add(maxHeap.extractMax());
        }
        Tools.assertSorted(list, true);
    }
}
