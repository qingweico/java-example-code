package algorithm.tree;

import org.junit.Test;
import util.RandomDataGenerator;
import util.Tools;

import java.util.ArrayList;

import static util.Print.print;

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
            binarySearchTree.add(RandomDataGenerator.randomInt(exclusive));
        }
        print(binarySearchTree.size());
        ArrayList<Integer> list = new ArrayList<>();
        while (!binarySearchTree.isEmpty()) {
            list.add(binarySearchTree.deleteMax());
        }
        Tools.assertSorted(list, true);
        print(list);
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
        print();
        binarySearchTree.preOrderNr();
    }
}
