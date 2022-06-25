package util;

import com.github.javafaker.Faker;

/**
 * Using faker to generate some random data, such as address, name, etc.
 */
public class RandomDataGenerator {
    public static String randomName(boolean isChinese) {
        Faker faker = new Faker();
        String ret = "";
        if (isChinese) {
            ret = faker.name().fullName();
        } else {
            ret = faker.name().firstName();
        }
        return ret;
    }

    public static String randomName() {
        return randomName(false);
    }
}
