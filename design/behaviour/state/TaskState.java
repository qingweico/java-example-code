package design.behaviour.state;

/**
 * @author zqw
 * @date 2023/11/5
 */
public interface TaskState {
    /**
     * 创建任务
     *
     * @param task Task
     */

    void create(Task task);

    /**
     * 开始任务
     *
     * @param task Task
     */
    void start(Task task);

    /**
     * 完成任务
     *
     * @param task Task
     */
    void finish(Task task);
}
