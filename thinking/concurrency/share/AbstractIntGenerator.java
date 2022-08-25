package thinking.concurrency.share;

/**
 * @author zqw
 * @date 2021/1/18
 */
public abstract class AbstractIntGenerator {

    private volatile boolean canceled = false;

    /**
     * next
     *
     * @return int
     */
    public abstract int next();

    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }


}
