package algorithm.tree;

import org.junit.Test;
import cn.qingweico.io.Print;
import cn.qingweico.supplier.RandomDataGenerator;
import cn.qingweico.supplier.Tools;

import java.util.ArrayList;

import static cn.qingweico.io.Print.println;

/**
 * @author zqw
 * @date 2023/6/2
 */
public class BinarySearchTreeTest {
    @Test
    public void testBst() {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        int size = 1000;
        int exclusive = 1000;
        for (int i = 0; i < size; i++) {
            binarySearchTree.add(RandomDataGenerator.rndInt(exclusive));
        }
        Print.println(binarySearchTree.size());
        ArrayList<Integer> list = new ArrayList<>();
        while (!binarySearchTree.isEmpty()) {
            list.add(binarySearchTree.deleteMax());
        }
        Tools.assertSorted(list, true);
        Print.println(list);
    }

    @Test
    public void testPrint() {
        int[] num = {5, 6, 1, 8, 3, 0, 9};
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        for (var item : num) {
            binarySearchTree.add(item);
        }
        binarySearchTree.printTree();
        binarySearchTree.reverse();
        binarySearchTree.printTree();
        binarySearchTree.levelOrder();
        println();
        binarySearchTree.preOrderNr();
    }
}
