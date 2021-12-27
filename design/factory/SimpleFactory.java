package design.factory;

import org.junit.Test;

import java.util.Scanner;

/**
 * 简单工厂
 *
 * @author:qiming
 * @date: 2021/12/22
 */
public class SimpleFactory {
    public Product createProduct(int type) {
        Product product;
        if (type == 1) {
            product = new Television();
        } else if (type == 2) {
            product = new Computer();
        } else {
            product = new DefaultProduct();
        }
        return product;
    }

    @Test
    public void originalClient() {
        // -Deditable.java.test.console=true(Edit Custom VM Options)
        // IDEA开启test代码块使用Scanner功能
        Scanner scanner = new Scanner(System.in);
        int type = scanner.nextInt();
        Product product;
        if (type == 1) {
            product = new Television();
        } else if (type == 2) {
            product = new Computer();
        } else {
            product = new DefaultProduct();
        }
        System.out.println(product);
        scanner.close();
    }

}

class SimpleFactoryClient {
    public static void main(String[] args) {
        SimpleFactory simpleFactory = new SimpleFactory();
        simpleFactory.createProduct(1);
    }
}

