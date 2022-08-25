package thinking.genericity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zqw
 * @date 2021/1/12
 */
public class NonCovariantArrays {
    public static void main(String[] args) {
        // Compiler Error: incompatible type
        // List<Fruit> list = new ArrayList<Apple>();
    }
}
