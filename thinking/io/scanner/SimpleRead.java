package thinking.io.scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author:周庆伟
 * @date: 2021/1/15
 */
public class SimpleRead {

    public static BufferedReader input = new BufferedReader(
            new StringReader("Sir Robin of Camelot\n22 1.61803"));

    public static void main(String[] args) {
        try {
            System.out.println("What's your name?");
            String name = input.readLine();
            System.out.println(name);
            System.out.println("How old are you? What's your s favorite double?");
            System.out.println("(input: <age> <double>)");
            String number = input.readLine();
            System.out.println(number);
            String[] numberArray = number.split(" ");
            int age = Integer.parseInt(numberArray[0]);
            double favorite = Double.parseDouble(numberArray[1]);
            System.out.format("Hi %s.\n", name);
            System.out.format("In 5 year ,you wil be %d.\n", age + 5);
            System.out.format("My favorite double is %f.", favorite / 2);

        } catch (IOException e) {
            System.err.println("I/O exception");
        }
    }
}
