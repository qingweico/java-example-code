package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author:qiming
 * @date: 2021/9/27
 */
public class RandomStringGenerator<T> implements Iterable<T> {

    private final List<T> list;

    public RandomStringGenerator(List<T> list) {
        this.list = list;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return list.get((int) (list.size() * Math.random()));
            }
        };
    }

    public static void main(String[] args) {
        var list = Arrays.asList("Tree", "List", "Array");
        var gen = new RandomStringGenerator<>(list);
        for (var item : gen) {
            System.out.println(item);
        }

        ArrayList<String> arr = new ArrayList<>(Arrays.asList("0", "1", "2"));
        // Passing the type
        arr.toArray(new String[0]);
        // Lazy evaluation
        arr.toArray(String[] :: new);
        for(var s : arr) {
           System.out.println(s);
        }

    }
}
