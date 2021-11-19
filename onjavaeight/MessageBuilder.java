package onjavaeight;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

import static util.Print.err;
import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/1/19
 */
@FunctionalInterface
interface Builder {
    void show();
}

public class MessageBuilder {
    public static void f() {
        print("f");
    }

    public static void main(String[] args) {
        // new MessageBuilder().show();

        // Static method introduction
        Builder staticIntro = MessageBuilder::f;
        staticIntro.show();


        // Object method introduction
        Consumer<MessageBuilder> objectMethodIntro = MessageBuilder::show;


        // Instance method introduction
        Builder instanceMethodIntro = new MessageBuilder()::show;
        instanceMethodIntro.show();

        // Constructor introduction
        Builder constructorIntro = MessageBuilder::new;
        constructorIntro.show();

        // Using Lambda
        Builder builder = () -> print("show");
        builder.show();


        // Using anonymous inner class
        Builder b = new Builder() {
            @Override
            public void show() {
                print("show");
            }
        };
        b.show();


    }

    public void show() {
        showLog("warn", () -> {
            // The this keyword points to the peripheral instance
            err("[" + Thread.currentThread().getName() + "]WARN: "
                    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss")
                    .format(new Date()) + " " + this + " warn message...");
        });

        // Good
        // showLog("warn", () -> f());

        // Lambda is preferred if the method and Lambda are in the same class.
        showLog("warn", MessageBuilder::f);
    }

    @Override
    public String toString() {
        return "MessageBuilder{}";
    }

    public static void showLog(String level, Builder builder) {
        if ("warn".equals(level)) {
            print("The show method is executed!");
            builder.show();
        }
    }
}
