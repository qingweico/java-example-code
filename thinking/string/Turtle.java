package thinking.string;

import java.io.PrintStream;
import java.util.Formatter;

/**
 * @author:周庆伟
 * @date: 2021/2/5
 */
public class Turtle {
    private final String name;
    private final Formatter f;

    public Turtle(String name, Formatter f) {
        this.name = name;
        this.f = f;
    }
    public void remove(int x ,int y) {
        f.format("%s The Turtle is at (%d, %d)\n", name, x, y);
    }

    public static void main(String[] args) {
        PrintStream outAlias = System.out;
        Turtle tommy = new Turtle("Tommy", new Formatter(System.err));
        Turtle terry = new Turtle("Terry", new Formatter(outAlias));
        tommy.remove(0, 0);
        terry.remove(4, 8);
        tommy.remove(3, 4);
        terry.remove(2, 5);
        tommy.remove(3, 3);
        terry.remove(3 ,3);

    }
}
