package design.factory;

import org.testng.annotations.Test;
import util.Constants;

import java.util.Scanner;

/**
 * 简单工厂
 *
 * @author zqw
 * @date 2021/12/22
 */
public class SimpleBaseFactoryTest {
    public Product createProduct(int type) {
        Product product;
        if (type == Constants.ONE) {
            product = new Television();
        } else if (type == Constants.TWO) {
            product = new Computer();
        } else {
            product = new DefaultProduct();
        }
        return product;
    }

    @Test
    public void original() {
        // -Deditable.java.test.console=true(Edit Custom VM Options)
        // IDEA开启test代码块使用Scanner功能
        Scanner scanner = new Scanner(System.in);
        int type = scanner.nextInt();
        Product product;
        if (type == Constants.ONE) {
            product = new Television();
        } else if (type == Constants.TWO) {
            product = new Computer();
        } else {
            product = new DefaultProduct();
        }
        System.out.println(product);
        scanner.close();
    }
    @Test
    public void simpleFactory() {
        SimpleBaseFactoryTest simpleFactory = new SimpleBaseFactoryTest();
        simpleFactory.createProduct(1);
    }

}
