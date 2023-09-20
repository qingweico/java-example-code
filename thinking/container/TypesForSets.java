package thinking.container;

import util.Print;

import java.util.*;

/**
 * @author zqw
 * @date 2021/2/9
 */
class TypesForSets {
    static <T> void fill(Set<T> set, Class<T> type) {
        try {
            int fill = 10;
            for (int i = 0; i < fill; i++) {
                set.add(type.getConstructor(int.class).newInstance(i));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static <T> void test(Set<T> set, Class<T> type) {
        fill(set, type);
        // Try to add duplicates
        fill(set, type);
        fill(set, type);
        Print.println(set);

    }

    public static void main(String[] args) {
        test(new HashSet<>(), HashType.class);
        test(new LinkedHashSet<>(), HashType.class);
        test(new TreeSet<>(), TreeType.class);
        // Thing doesn't work!
        test(new HashSet<>(), SetType.class);
        test(new HashSet<>(), TreeType.class);
        test(new LinkedHashSet<>(), SetType.class);
        test(new LinkedHashSet<>(), TreeType.class);

        try {
            test(new TreeSet<>(), SetType.class);

        } catch (Exception e) {
            Print.println(e.getMessage());
        }


    }
}

class SetType {
    int i;

    public SetType(int i) {
        this.i = i;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SetType && (i == ((SetType) o).i);
    }

    @Override
    public String toString() {
        return Integer.toString(i);
    }
}

class HashType extends SetType {

    public HashType(int i) {
        super(i);
    }

    @Override
    public int hashCode() {
        return i;
    }
}

class TreeType extends SetType implements Comparable<TreeType> {

    public TreeType(int i) {
        super(i);
    }

    @Override
    public int compareTo(TreeType arg) {
        return Integer.compare(arg.i, i);
    }
}
