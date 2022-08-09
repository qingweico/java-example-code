package effective;


import annotation.Pass;

import java.util.*;
import java.util.function.DoubleBinaryOperator;

/**
 * Lambda优先于匿名类
 *
 * @author zqw
 * @date 2021/2/4
 */

// An interface with an abstract method (or, almost always, a class that is not abstract)
// is a function type whose instances are called function objects representing the
// function or the action to be taken.
@Pass
@SuppressWarnings("all")
class Article42 {
    public static void main(String[] args) {

        List<String> words = Arrays.asList("token", "more", "not");
        // Anonymous class instance as a function object - obsolete!
        // Sort by the length of string
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });
        System.out.println(words);

        // Lambda expression as function object(replace anonymous class)
        // The compiler uses a process called type deduction to derive the parameter
        // types and return value types from the context.
        // tips: removes all types of the lambda arguments unless their presence makes the program clearer!
        Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));

        // Java8 adds a sort method to the List interface
        words.sort(Comparator.comparingInt(String::length));

    }

}
// Enumeration instance fields take precedence over constant-specific class bodies
// PLUS("+", (x ,y) -> x + y) {}: Those in parentheses are called instance fields,
// and those inside curly braces are called constant-specific class bodies.

enum OperationOfUsingLambda {
    /**
     * plus
     */
    PLUS("+", Double::sum),
    /**
     * minus
     */
    MINUS("-", (x, y) -> x - y),
    /**
     * times
     */
    TIMES("*", (x, y) -> x * y),
    /**
     * divide
     */
    DIVIDE("/", (x, y) -> x / y),

    /**
     * [other] User Customized
     */
    MOD("%", (x, y) -> x % y) {
        // Cannot be referenced from a static context: why?
        // System.out.println(this);
        // nothing to do here;
    };

    public double apply(double a, double b) {
        return op.applyAsDouble(a, b);
    }

    private final String symbol;
    private final DoubleBinaryOperator op;

    OperationOfUsingLambda(String symbol, DoubleBinaryOperator operator) {
        this.symbol = symbol;
        op = operator;
        System.out.printf("[%s]: exec constructor!%n", this);
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]: " + symbol;
    }

    public static void main(String[] args) {
        double apply = OperationOfUsingLambda.PLUS.apply(12, 32);
        System.out.println(apply);
        double mod = OperationOfUsingLambda.MOD.apply(2, 4);
        System.out.println(mod);
    }

    // But Lambda is limited to function interfaces. If you want to create an instance
    // of an abstract class, you can only do so using anonymous classes, not using
    // Lambda. Same thing.You can use anonymous classes to create instances of
    // interfaces with multiple abstract methods.


    // Finally, Lambda cannot get a reference to itself.
    // In Lambda, the keyword this points to the peripheral instance, and in anonymous
    // classes, the keyword this points to the anonymous class instance.
    // If you need to access a function object from inside its body, you must use an
    // anonymous class.

    // Never serialize a Lambda(or an anonymous class instance) if possible.

    // Never use an anonymous class for a function object unless you must create an
    // instance of a non-function interface.
}