package design.behaviour.state.impl;

import design.behaviour.state.Task;
import design.behaviour.state.TaskState;
import cn.qingweico.io.Print;

/**
 * @author zqw
 * @date 2023/11/5
 */
public class StartState implements TaskState {

    @Override
    public void create(Task task) {
        Print.err("任务已创建 不能重复创建", task);
    }

    @Override
    public void start(Task task) {
        Print.grace("任务开始", task);
        task.setState(new FinishState());
    }

    @Override
    public void finish(Task task) {
        Print.err("任务未开始 不能完成", task);
    }
    @Override
    public String toString() {
        return "Start";
    }
}
