package thinking.genericity;

import java.util.Iterator;

/**
 * Adapt the Fibonacci class to make it Iterator.
 * create a adapter
 *
 * @author zqw
 * @date 2021/4/9
 */
public class IterableFibonacci extends Fibonacci implements Iterable<Integer> {
    private int n;

    public IterableFibonacci(int count) {
        n = count;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return n > 0;
            }

            @Override
            public Integer next() {
                n--;
                return IterableFibonacci.this.next();
            }

            @Override
            public void remove() { // Not implemented
                throw new UnsupportedOperationException();
            }

        };
    }

    public static void main(String[] args) {
        for (int i : new IterableFibonacci(18)) {
            System.out.print(i + " ");
        }
    }
}
