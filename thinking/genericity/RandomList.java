package thinking.genericity;

import cn.qingweico.io.Print;
import java.util.ArrayList;
import java.util.Random;


/**
 * @author zqw
 * @date 2021/3/23
 */
public class RandomList<T> {
    private final ArrayList<T> storage = new ArrayList<>();
    private final Random rand = new Random(47);

    public void add(T item) {
        storage.add(item);
    }

    public T select() {
        return storage.get(rand.nextInt(storage.size()));
    }

    public static void main(String[] args) {
        RandomList<String> rList = new RandomList<>();
        for (String s : "to be or not to be".split(" ")) {
            rList.add(s);
        }
        for (int i = 0; i < 10; i++) {
            Print.prints(rList.select());
        }
    }
}
