package design.behaviour.state.impl;

import design.behaviour.state.Task;
import design.behaviour.state.TaskState;
import util.Print;

/**
 * @author zqw
 * @date 2023/11/5
 */
public class FinishState implements TaskState {
    @Override
    public void create(Task task) {
        Print.err("任务已经开始 不能创建", task);
    }

    @Override
    public void start(Task task) {
        Print.err("任务已经开始 不能重复开始", task);
    }

    @Override
    public void finish(Task task) {
        Print.grace("完成任务", task);
        task.setState(new OverState());
    }
    @Override
    public String toString() {
        return "Finish";
    }
}
