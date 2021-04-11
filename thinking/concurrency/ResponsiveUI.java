package thinking.concurrency;


import static util.Print.print;

/**
 * User interface responsiveness
 *
 * @author:qiming
 * @date: 2021/1/18
 * {RunByHand}
 */
public class ResponsiveUI extends Thread{
    private static volatile double d = 1;
    public ResponsiveUI(){
        setDaemon(true);
        start();
    }
    public void run() {
        while (d > 0) {
            d = d + (Math.PI + Math.E) / d;
        }
    }

    public static void main(String[] args) throws Exception {

        // Must kill this process, because UnresponsiveUI will perform the operation
        // in an infinite while loop.
        //new UnresponsiveUI();

        new ResponsiveUI();
        System.in.read();

        // Show progress
        print(d);
    }

}
class UnresponsiveUI {
    private volatile double d = 1;
    public UnresponsiveUI() throws Exception {
        while (d > 0) {
            d = d + (Math.PI + Math.E) / d;
        }

        // Never get here
        System.in.read();
    }
}
