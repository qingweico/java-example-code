package thread.queue.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * LongEvent工厂
 *
 * @author zqw
 * @date 2022/8/7
 */
class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
