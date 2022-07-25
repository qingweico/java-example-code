package util;

import com.github.javafaker.Faker;
import org.apache.commons.math3.random.RandomGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Using faker to generate some random data, such as random number, address, name, etc.
 *
 * @author zqw
 * @date 2022/06/20
 */
public class RandomDataGenerator {
    private final static Random R = new Random();
    private final static ThreadLocalRandom TLR = ThreadLocalRandom.current();

    public static String randomName(boolean isChinese) {
        String ret;
        if (isChinese) {
            ret = new Faker(Locale.CHINA).name().fullName();
        } else {
            ret = new Faker().name().fullName();
        }
        return ret;
    }

    public static String randomAddress(boolean isChinese) {
        String ret;
        if (isChinese) {
            ret = new Faker(Locale.CHINA).address().fullAddress();
        } else {
            ret = new Faker().address().fullAddress();
        }
        return ret;
    }

    public static String randomAddress() {
        return randomAddress(false);
    }

    public static String randomName() {
        return randomName(false);
    }

    public static int randomInt() {
        return R.nextInt(100);
    }

    public static int randomInt(int bound) {
        return R.nextInt(bound);
    }


    public static Date randomDate() {
        Calendar calendar = Calendar.getInstance();
        long currentTimeMillis = System.currentTimeMillis();
        calendar.setTimeInMillis(currentTimeMillis);
        // TODO 生成随机日期
        calendar.getTime();
        return new Date();
    }

    public static int safeRandomInt() {
        return TLR.nextInt();
    }

    public static int safeRandomInt(int bound) {
        return TLR.nextInt(bound);
    }

    public static boolean tf() {
        return R.nextBoolean();
    }

    public static void main(String[] args) {
        System.out.println(randomDate());
    }
}
