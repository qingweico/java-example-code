package oak.lambda;

import util.Print;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

import static util.Print.err;

/**
 * --------------- Four Method References ---------------
 *
 * @author zqw
 * @date 2021/1/19
 */
class MessageBuilder {
    private static final String DEFAULT_LEVEL = "INFO";
    private static final String DEBUG_LEVEL = "DEBUG";

    public static void printMessage() {
        Print.println("printMessage");
    }

    public MessageBuilder() {
        System.out.println("MessageBuilder() constructor");
    }

    public static void main(String[] args) {
        // Static method reference
        Builder staticMethodRefBuilder = MessageBuilder::printMessage;
        staticMethodRefBuilder.show();

        // Instance method reference
        Builder instanceMethodRef = new MessageBuilder()::log;
        instanceMethodRef.show();

        // Constructor reference
        Builder constructorRef = MessageBuilder::new;
        constructorRef.show();

        // Object method reference
        Consumer<MessageBuilder> consumer = MessageBuilder::log;
        consumer.accept(new MessageBuilder());
    }

    public void log() {
        this.log(DEFAULT_LEVEL);
    }

    public void log(String level) {
        log(level, () -> {
            // The 'this' keyword points to the peripheral instance.
            err("[" + Thread.currentThread().getName() + "]" + level + ": "
                    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date()) + " " + this);
        });

        // Good
        // log(level, () -> printMessage());

        // Lambda is preferred if the method and Lambda are in the same class.
        log(level, MessageBuilder::printMessage);
    }

    @Override
    public String toString() {
        return "[MessageBuilder]";
    }

    public void log(String level, Builder builder) {
        if (DEBUG_LEVEL.equals(level)) {
            System.out.print(DEBUG_LEVEL);
            builder.show();
        } else {
            log();
        }
    }
}
@FunctionalInterface
interface Builder {
    /**
     * show();
     */
    void show();
}

