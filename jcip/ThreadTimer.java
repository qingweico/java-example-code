package jcip;

import cn.qingweico.datetime.DateUtil;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 不推荐使用 Timer 类
 * 1 Timer 使用绝对时间
 * 2 Timer 使用单线程处理任务, 长时间运行的任务可能会阻塞其他任务
 * 3 一旦抛出异常 Timer 将会终止, 后面的任务无法继续执行
 * @author zqw
 * @date 2021/4/7
 */
@SuppressWarnings("all")
class ThreadTimer {
    public static void main(String[] args) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                // Output system time every second
                System.out.println(DateUtil.now());
                // t.cancel();
            }
        }, new Date(), 1000);
    }
}
