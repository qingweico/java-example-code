package thinking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zqw
 * @date 2020/10/11
 */


public class FiledList<T> {
    private final Class<T> type;

    public FiledList(Class<T> type) {
        this.type = type;
    }

    public List<T> create(int nElement) {
        List<T> result = new ArrayList<>();
        try {
            for (int i = 0; i < nElement; i++) {
                result.add(type.getConstructor().newInstance());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void main(String[] args) {
        FiledList<CountedInteger> list = new FiledList<>(CountedInteger.class);
        System.out.println(list.create(15));

    }
}

class CountedInteger {
    private static long count;
    private final long id = count++;

    public CountedInteger() {
    }

    @Override
    public String toString() {
        return Long.toString(id);
    }
}