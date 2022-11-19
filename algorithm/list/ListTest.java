package algorithm.list;


import org.testng.annotations.Test;
import util.constants.Constants;

import static util.Print.print;

/**
 * @author zqw
 * @date 2022/2/12
 */
public class ListTest {
    @Test
    public void testRev() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < Constants.TEN; i++) {
            linkedList.addLast(i);
        }
        print(linkedList);
        linkedList.dummy.next = linkedList.reverseListR(linkedList.dummy.next);
        print(linkedList);
    }
}
