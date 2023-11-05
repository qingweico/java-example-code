package design.behaviour.state;

import design.behaviour.state.impl.CreateState;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zqw
 * @date 2023/11/5
 */
@Data
public class Task {
    private AtomicInteger ai = new AtomicInteger(0);

    private String seq;

    private TaskState state = new CreateState();

    public Task(String seq) {
        this.seq = seq;
    }

    public void create() {
        ai.getAndIncrement();
        state.create(this);
    }

    public void start() {
        ai.getAndIncrement();
        state.start(this);
    }

    public void finish() {
        ai.getAndIncrement();
        state.finish(this);
    }

    @Override
    public String toString() {
        return "Task{" + "ai=" + ai +
                ", seq='" + seq + '\'' +
                ", state=" + state +
                '}';
    }
}
