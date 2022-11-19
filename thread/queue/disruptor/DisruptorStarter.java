package thread.queue.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import thread.pool.CustomThreadFactory;
import util.constants.Constants;

import java.nio.ByteBuffer;

/**
 * @author zqw
 * @date 2022/8/7
 */
public class DisruptorStarter {
    public static void main(String[] args) throws InterruptedException {
        int bufferSize = Constants.KB;
        LongEventFactory factory = new LongEventFactory();
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory,
                bufferSize, CustomThreadFactory.basicThreadFactory(),
                /*ProducerType.single和ProducerType.MULTI 一个生产者用single 多个用multi*/
                /*生产和消费的策略请看com.lmax.disruptor.WaitStrategy*/
                ProducerType.SINGLE, new YieldingWaitStrategy());
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; l < Constants.NUM_100; l++) {
            bb.putLong(0, l);
            producer.onData(bb);
        }
        disruptor.shutdown();
    }
}
