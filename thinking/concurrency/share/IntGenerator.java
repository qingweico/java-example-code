package thinking.concurrency.share;

/**
 * @author:周庆伟
 * @date: 2021/1/18
 */
public abstract class IntGenerator {

    private volatile boolean canceled = false;

    public abstract int next();

    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }


}
