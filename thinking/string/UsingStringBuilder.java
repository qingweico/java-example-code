package thinking.string;

import java.util.Random;


public class UsingStringBuilder {
    public static Random random = new Random();

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < 9; i++) {
            result.append(random.nextInt(5));
            result.append(", ").append(i);

        }
        // [)
        result.delete(result.length() - 2, result.length());
        result.append("]");
        return result.toString();
    }

    public static void main(String[] args) {
        UsingStringBuilder usb = new UsingStringBuilder();
        System.out.println(usb);
    }
}
