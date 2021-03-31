package effective;

import util.Print;

import java.util.*;

import static util.Print.*;

/**
 * 慎用重载
 * 始终可以给方法起不同的名称，而不使用重载机制
 */
class CollectionClassifier {

    // Using this method will print three times "Unknown Collection"
    public static String classify(Set<?> set) {
        return "Set";
    }

    public static String classify(List<?> list) {
        return "List";
    }

    public static String classify(Collection<?> c) {
        return "Unknown Collection";
    }

    // Best modification method:
    // It will print "Set List Unknown Collection".
    public static String classifyPlus(Collection<?> c) {
        return c instanceof Set ? "Set" :
                c instanceof List ? "List" : "Unknown Collection";
    }

    public static void main(String[] args) {
        Collection<?>[] collections = {
                new HashSet<>(),
                new ArrayList<>(),
                new HashMap<String, String>().values()};
        // Which overloaded method to call is a decision that is made at compile time.
        for (Collection<?> c : collections) {
            System.out.println(CollectionClassifier.classify(c));
        }
        for (Collection<?> c : collections) {
            System.out.println(CollectionClassifier.classifyPlus(c));
        }
    }
}


/**
 * 葡萄酒
 */
class Wine {
    String name() {
        return "wine";
    }
}

/**
 * 气泡葡萄酒
 */
class SparklingWine extends Wine {
    String name() {
        return "sparkling wine";
    }
}

/**
 * 香槟
 */
class Champagne extends SparklingWine {
    String name() {
        return "champagne";
    }
}

public class Article52 {
    public static void main(String[] args) {
        List<Wine> wineList = List.of(
                new Wine(),
                new SparklingWine(),
                new Champagne()
        );
        // The instance's compile-time type is Wine and which overriding method is
        // called depends on the specific runtime type of the object.
        for (Wine wine : wineList) {
            // wine,sparkling wine,champagne
            System.out.println(wine.name());
        }
    }
}

class SetList {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();

        for (int i = -3; i < 3; i++) {
            set.add(i);
            list.add(i);
        }

        for (int i = 0; i < 3; i++) {
            set.remove(i);
            list.remove((Integer) i); //boolean remove(Object o);
            //list.remove(i); 选择该重载方法 E remove(int index); 会改变集合中元素的顺序
        }
        System.out.println(set + " " + list);
    }
}


class PrintUtil {

    public void addNumber(int x) {
        prints(x + 4);
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 4, 5, 6);
        // Call the System.out.println() method foreach element that enters the foreach.
        list.forEach(Print::prints);
        print();
        list.forEach(new PrintUtil()::addNumber);
    }
}
