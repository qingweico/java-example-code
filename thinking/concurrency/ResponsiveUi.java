package thinking.concurrency;


import annotation.Pass;
import util.Print;

/**
 * User interface responsiveness
 *
 * @author zqw
 * @date 2021/1/18
 * {RunByHand}
 */
@Pass
@SuppressWarnings("all")
class ResponsiveUi extends Thread {
    private static volatile double d = 1;

    public ResponsiveUi() {
        setDaemon(true);
        start();
    }

    @Override
    public void run() {
        while (d > 0) {
            d = d + (Math.PI + Math.E) / d;
        }
    }

    public static void main(String[] args) throws Exception {

        // Must kill this process, because UnresponsiveUi will perform the operation
        // in an infinite while loop.
        // new UnresponsiveUi();

        new ResponsiveUi();
        System.out.println(System.in.read());

        // Show progress
        Print.println(d);
    }

}
@Pass
@SuppressWarnings("all")
class UnresponsiveUi {
    private volatile double d = 1;

    public UnresponsiveUi() throws Exception {
        while (d > 0) {
            d = d + (Math.PI + Math.E) / d;
        }

        // Never get here
        System.out.println(System.in.read());
    }
}
