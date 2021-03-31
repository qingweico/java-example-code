package onjava8;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/2/24
 */
@FunctionalInterface
interface Walk {
    void walking();
}
public class InstanceMethodIntro {
    public void walking() {
        print("ObjectMethodIntro walking...");
    }

    public static void main(String[] args) {
        // Using anonymous inner class
        InstanceMethodIntro instanceMethodIntro = new InstanceMethodIntro();
        Walk walk = new Walk() {
            @Override
            public void walking() {
                instanceMethodIntro.walking();
            }
        };
        walk.walking();

        // Using lambda
        Walk l = () -> {
            instanceMethodIntro.walking();
        };
        l.walking();

        // Using Instance method introduction
        Walk  w = instanceMethodIntro::walking;
        w.walking();
    }

}
