package thinking.holding;


import java.util.*;

/**
 * @author:qiming
 * @date: 2021/3/21
 */
public class AddingGroups {
    public static void main(String[] args) {
        Collection<Integer> collection = new ArrayDeque<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println(collection);
        Integer[] moreInts = {6, 7, 8, 9, 10};
        collection.addAll(Arrays.asList(moreInts));
        // Runs significantly faster , but you can't constructor a Collection this way.
        Collections.addAll(collection, 11, 12, 13, 14, 15);
        Collections.addAll(collection, moreInts);
        List<Integer> list = Arrays.asList(16, 17, 18, 19, 20);
        list.set(1, 99);
        // Runtime error because the underlying array cannot be resized.
        // list.add(22);

    }
}
