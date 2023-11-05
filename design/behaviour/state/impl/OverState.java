package design.behaviour.state.impl;

import design.behaviour.state.Task;
import design.behaviour.state.TaskState;
import util.Print;

/**
 * @author zqw
 * @date 2023/11/5
 */
public class OverState implements TaskState {

    @Override
    public void create(Task task) {
        Print.err("任务已经完成 不能操作", task);
    }

    @Override
    public void start(Task task) {
        Print.err("任务已经完成 不能操作", task);
    }

    @Override
    public void finish(Task task) {
        Print.err("任务已经完成 不能操作", task);
    }
    @Override
    public String toString() {
        return "Over";
    }
}
