package onjava8.stream;

import java.util.Optional;

/**
 * @author:qiming
 * @date: 2021/9/27
 */
public class Opt {
    public static void main(String[] args) {
        Optional<Integer> x =  Optional.empty();
        var y = x.map(i -> i * i);
        System.out.println(y);
    }
}
