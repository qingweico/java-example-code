package jcip;

import annotation.Pass;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Timer will be used infrequently in JDK5.0 or later
 *
 * @author zqw
 * @date 2021/4/7
 */
@Pass
@SuppressWarnings("all")
class OutOfTime {
    public static void main(String[] args) throws InterruptedException {
        // Timer creates only one thread while executing all timed tasks.
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        TimeUnit.SECONDS.sleep(1);
        // An exception message will be thrown - Timer already cancelled.
        timer.schedule(new ThrowTask(), 1);
        TimeUnit.SECONDS.sleep(5);
    }
    static class ThrowTask extends TimerTask {

        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}
