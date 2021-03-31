package jcip;

/**
 * @author:qiming
 * @date: 2021/3/30
 */
public class TestSynchronizedAndCas {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        SimulatedCAS simulatedCAS = new SimulatedCAS();
        CasCounter casCounter = new CasCounter(simulatedCAS);
        for(int i = 0;i < 30;i++) {
            new Thread(() -> {
                for(int j = 0;j < 10000;j++) {
                    casCounter.increment();
                }
            }).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(casCounter.getValue());
        System.out.println(System.currentTimeMillis() - start + "ms");

        long start0 = System.currentTimeMillis();
        SynchronizedInteger integer = new SynchronizedInteger();
        for(int i = 0;i < 30;i++) {
            new Thread(() -> {
                for(int j = 0;j < 10000;j++) {
                    integer.increment();
                }
            }).start();



        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(integer.get());
        System.out.println(System.currentTimeMillis() - start0 + "ms");
    }

}
