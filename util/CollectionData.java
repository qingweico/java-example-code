package util;

import java.util.ArrayList;

/**
 * @author:qiming
 * @date: 2021/4/8
 */
public class CollectionData<T> extends ArrayList<T> {
    public CollectionData(Generator<T> gen, int quantity) {
        for(int i = 0; i < quantity; i++) {
            add(gen.next());
        }
    }
    // A generic convenience method:
    public static <T> CollectionData<T> list(Generator<T> gen, int quantity) {
        return new CollectionData<T>(gen, quantity);
    }
}