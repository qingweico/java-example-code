package thinking.string;

/**
 * @author:周庆伟
 * @date: 2021/2/5
 */
public class SimpleFormat {
    public static void main(String[] args) {
        int x = 5;
        double y = 5.332542;
        System.out.println("Row 1: [" + x + " " + y + "]");

        // Java SE5
        // So printf() is equivalent to format()
        System.out.format("Row 1: [%d %f]\n", x , y);

        System.out.printf("Row 1: [%d %f]\n", x , y);


    }
}
