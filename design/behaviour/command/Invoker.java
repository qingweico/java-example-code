package design.behaviour.command;

/**
 * @author zqw
 * @date 2023/10/15
 */
class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }
    public void executeCommand() {
        command.execute();
    }
}
