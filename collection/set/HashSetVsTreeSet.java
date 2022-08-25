package collection.set;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author zqw
 * @date 2021/9/27
 */
public class HashSetVsTreeSet {
    public static void main(String[] args) {
        var hashSet = new HashSet<Integer>();
        hashSet.add(88);
        hashSet.add(1);
        hashSet.add(23);
        hashSet.add(41);
        System.out.println(hashSet.stream().
                map(Object::toString).
                collect(Collectors.joining(",")));


        Integer x = 23;
        var treeSet = new TreeSet<Integer>() {
            {
                add(88);
                add(1);
                add(23);
                add(41);
            }
        };

        var lowerThanX = treeSet.lower(x);
        var greaterThanX = treeSet.higher(x);
        System.out.println(lowerThanX + " " + greaterThanX);
        var lowerOrEqualThanX = treeSet.floor(x);
        var greaterOrEqualThanX = treeSet.ceiling(x);
        System.out.println(lowerOrEqualThanX + " " + greaterOrEqualThanX);


        System.out.println(treeSet.stream().
                map(Object::toString).
                collect(Collectors.joining(",")));

    }
}
