package thread.queue.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 消费者 处理任务
 *
 * @author zqw
 * @date 2022/8/7
 */
class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.printf("%s, [event = %s], [l = %s], [b = %s]%n",
                Thread.currentThread().getName(), longEvent.getValue(),l,b);
    }
}

