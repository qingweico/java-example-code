package jcip;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author:qiming
 * @date: 2021/4/7
 */
public class ThreadTimer {
    public static void main(String[] args) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                // Output system time every second
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                // t.cancel();
            }
        }, new Date(), 1000);
    }
}
