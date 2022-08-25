package thinking.concurrency.atom;

import thinking.concurrency.share.EvenChecker;
import thinking.concurrency.share.AbstractIntGenerator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zqw
 * @date 2021/1/20
 */
class AtomicEvenGenerator extends AbstractIntGenerator {
    private final AtomicInteger ai = new AtomicInteger(0);
    @Override
    public int next() {
        return ai.addAndGet(2);
    }

    public static void main(String[] args) {
        EvenChecker.test(new AtomicEvenGenerator());
    }
}
