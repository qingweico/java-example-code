package design.behaviour.command;

/**
 * 命令模式是一种行为设计模式, 它允许将请求封装为一个对象, 从而允许用不同的请求对客户端进行参数化
 * 对请求排队或记录请求日志, 并支持可撤销的操作
 *
 * @author zqw
 * @date 2023/10/15
 */
class Starter {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command = new ConcreteCommand(receiver);
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.executeCommand();
    }
}
