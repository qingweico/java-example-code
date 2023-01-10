package oak.newfeature;

/**
 * 对于空指针的处理,可以借助编译器,使用封闭类和模式匹配对返回值做强制检查
 * 处理npe,不尽人意的 Optional {@link java.util.Optional}
 *
 * @author zqw
 * @date 2022/12/30
 */
class NpeHandler {

    private static void hasMessage(Message message, String msg) {
        switch (message.payload()) {
            case Returned.Undefined undefined -> System.out.println(undefined);
            case Returned.ReturnValue<String> rv -> {
                String returnedMsg = rv.rv();
                System.out.println(returnedMsg.equals(msg));
            }
            default -> throw new IllegalStateException("Unexpected value: " + message.payload());
        }
    }

    public static void main(String[] args) {
        String msg = "This is message!";
        Message message = new Message("Welcome!");
        hasMessage(message, msg);
        Message nullMeg = new Message(null);
        hasMessage(nullMeg, msg);
    }
}

sealed interface Returned<T> {

    record ReturnValue<T>(T rv) implements Returned<T> {
    }

    record Undefined(String tip) implements Returned<String> {
        @Override
        public String toString() {
            return tip;
        }
    }

    record ErrorCode(int code) implements Returned<Integer> {}
}

final class Message {
    private final String msg;

    public Message(String msg) {
        this.msg = msg;
    }

    public Returned<String> payload() {
        if (msg == null) {
            return new Returned.Undefined(this.getClass().getSimpleName() + "[msg is null!]");
        }
        return new Returned.ReturnValue<>(msg);
    }
}
