package oak;

import java.util.Scanner;

/**
 * 二元一次方程组
 *
 * @author zqw
 * @date 2022/5/7
 */
public class QuadraticEquation {
    private Double a;
    private Double b;
    private Double c;

    public double getDiscriminant() {
        return Math.pow(b, 2) - a * c * 4;
    }

    private double getRoot1() {
        double result = getDiscriminant();
        if (result < 0) {
            return 0;
        }
        return (-b + Math.sqrt(result)) / (2 * a * 1.0);

    }

    public double getRoot2() {
        double result = getDiscriminant();
        if (result < 0) {
            return 0;
        }
        return (-b - Math.sqrt(result)) / (2 * a * 1.0);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        QuadraticEquation quadraticEquation = new QuadraticEquation();
        Scanner scanner = new Scanner(System.in);
        quadraticEquation.a = scanner.nextDouble();
        quadraticEquation.b = scanner.nextDouble();
        quadraticEquation.c = scanner.nextDouble();
        if (quadraticEquation.getDiscriminant() == 0) {
            System.out.println("The equation has one root " + quadraticEquation.getRoot1());
        } else if (quadraticEquation.getRoot1() == 0) {
            System.out.println("The equation has no real roots");
        } else {
            System.out.println("The equation has two roots " + quadraticEquation.getRoot1() + " and " + quadraticEquation.getRoot2());
        }
        System.out.println("time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
