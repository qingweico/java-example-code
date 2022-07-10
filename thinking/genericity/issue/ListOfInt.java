package thinking.genericity.issue;

import java.util.ArrayList;
import java.util.List;

/**
 * Autoboxing compensates for the inability to use primitive in generics.
 *
 * @author:qiming
 * @date: 2021/1/20
 */
class ListOfInt {
    public static void main(String[] args) {
        // 不推荐;性能损耗
        List<Integer> li = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            // packing
            li.add(i);
        }
        for (int i : li) {
            // unpacking
            System.out.print(i + " ");
        }
    }
}
