package design.behaviour.command;

/**
 * @author zqw
 * @date 2023/10/15
 */
class ConcreteCommand implements Command {

    private final Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.action();
    }
}
