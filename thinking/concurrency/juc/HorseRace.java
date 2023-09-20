package thinking.concurrency.juc;

import util.Print;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Using CyclicBarriers
 *
 * @author zqw
 * @date 2021/2/1
 */
class HorseRace {
    static final int FINISH_LINE = 75;
    private final List<Horse> horses = new ArrayList<>();
    private final ExecutorService exec = Executors.newCachedThreadPool();

    public HorseRace(int nHorse, final int pause) {

        CyclicBarrier barrier = new CyclicBarrier(nHorse, () -> {
            StringBuilder s = new StringBuilder();
            // The fence on the racetrack
            s.append("=".repeat(FINISH_LINE));
            Print.println(s);
            for (Horse horse : horses) {
                Print.println(horse.tracks());
            }
            for (Horse horse : horses) {
                if (horse.getStrides() > FINISH_LINE) {
                    Print.println(horse + "won!");
                    exec.shutdownNow();
                    return;

                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(pause);
            } catch (InterruptedException ex) {
                Print.println("barrier-action sleep interrupted");
            }
        });
        for (int i = 0; i < nHorse; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            exec.execute(horse);

        }
    }

    public static void main(String[] args) {
        int nHorse = 7;
        int pause = 200;

        // Optional argument
        if (args.length > 0) {
            int n = Integer.parseInt(args[0]);
            nHorse = n > 0 ? n : nHorse;
        }
        // Optional argument
        if (args.length > 1) {
            int p = Integer.parseInt(args[1]);
            pause = p > -1 ? p : pause;
        }
        new HorseRace(nHorse, pause);
    }
}

class Horse implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private final Random random = new Random(47);
    private static CyclicBarrier barrier;

    public Horse(CyclicBarrier cyclicBarrier) {
        barrier = cyclicBarrier;
    }

    public synchronized int getStrides() {
        return strides;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    // Producer 0 1 or 2
                    strides += random.nextInt(3);
                }
                barrier.await();
            }
        } catch (InterruptedException ex) {
            // a legitimate way to exit
        } catch (BrokenBarrierException ex) {
            // This one we want to know about
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        return "Horse " + id + " ";
    }

    public String tracks() {
        return "*".repeat(Math.max(0, getStrides())) + id;
    }

}
