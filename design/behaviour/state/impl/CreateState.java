package design.behaviour.state.impl;

import design.behaviour.state.Task;
import design.behaviour.state.TaskState;
import util.Print;

/**
 * @author zqw
 * @date 2023/11/5
 */
public class CreateState implements TaskState {

    public void create(Task task) {
        Print.grace("任务创建", task);
        task.setState(new StartState());
    }

    @Override
    public void start(Task task) {
        Print.err("任务未创建 不能开始", task);
    }

    @Override
    public void finish(Task task) {
        Print.err("任务未创建 不能完成", task);
    }

    @Override
    public String toString() {
        return "Create";
    }
}
