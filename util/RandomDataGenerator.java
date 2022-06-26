package util;

import com.github.javafaker.Faker;

import java.util.Locale;

/**
 * Using faker to generate some random data, such as address, name, etc.
 *
 * @author zqw
 * @date 2022/06/20
 */
public class RandomDataGenerator {
    public static String randomName(boolean isChinese) {
        String ret;
        if (isChinese) {
            ret = new Faker(Locale.CHINA).name().fullName();
        } else {
            ret = new Faker().name().fullName();
        }
        return ret;
    }

    public static String randomAddress() {
        return new Faker(Locale.CHINA).address().streetAddress();
    }

    public static String randomName() {
        return randomName(false);
    }

    public static void main(String[] args) {
        System.out.println(randomAddress());
    }
}
