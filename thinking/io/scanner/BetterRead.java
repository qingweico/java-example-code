package thinking.io.scanner;

import java.util.Scanner;

/**
 * @author:qiming
 * @date: 2021/1/15
 */
public class BetterRead {
    public static void main(String[] args) {

        // The Scanner constructor can accept any type of input object, including a
        // File object, an InputStream, a String, or a Readable object.
        Scanner stdin = new Scanner(SimpleRead.input);
        System.out.println("What your name?");
        String name = stdin.nextLine();
        System.out.println(name);
        System.out.println("How old are you? What's your s favorite double?");
        System.out.println("(input: <age> <double>)");
        int age = stdin.nextInt();
        double favorite = stdin.nextDouble();
        System.out.println(age);
        System.out.println(favorite);
        System.out.format("Hi %s.\n", name);
        System.out.format("In 5 year ,you wil be %d.\n", age + 5);
        System.out.format("My favorite double is %f.", favorite / 2);

    }
}
