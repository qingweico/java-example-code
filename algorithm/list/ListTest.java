package algorithm.list;


import org.testng.annotations.Test;
import util.Print;
import util.constants.Constants;

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
        Print.println(linkedList);
        linkedList.dummy.next = linkedList.reverseListR(linkedList.dummy.next);
        Print.println(linkedList);
    }
}
