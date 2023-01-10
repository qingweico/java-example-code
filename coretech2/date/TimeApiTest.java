package coretech2.date;

import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

/**
 * @author zqw
 * @date 2022/8/2
 */
public class TimeApiTest {
    public static void main(String[] args) throws InterruptedException {
        // 10亿年前 <-------------- 1970-01-01 00:00:00
        System.out.println(Instant.MIN);
        // 1970-01-01 00:00:00 --------------> 10亿年后
        System.out.println(Instant.MAX);
        timeCost();
    }

    public static void timeCost() {
        // 从最佳的可用系统时钟中获取当前的时间
        Instant start = Instant.now();
        // something else
        Instant end = Instant.now();
        // Duration是两个时刻之间的时间量
        Duration timeElapsed = Duration.between(start, end);
        long millis = timeElapsed.getSeconds();
        System.out.printf("time: [%sms]%n", millis);
    }

    @Test
    public void duration() {
        // 产生一个给定数量的指定时间单位的时间间隔
        // Duration#ofxxx(long number)
        Duration hours10 = Duration.ofHours(10);
        Duration hours20 = Duration.ofHours(20);
        // toXXXPart(): 获取当前时长中给定时间单位的部分
        System.out.println(hours10.toHoursPart());
        // hours10 * 2 - hours20 < 0?
        // isZero()
        System.out.println(hours10.multipliedBy(2).minus(hours20).isNegative());
    }

    @Test
    public void localDate() {
        LocalDate today = LocalDate.now();
        System.out.println(today);
        // Alonzo Church lambda 演算的发明者
        LocalDate alonzoBirthday = LocalDate.of(1903, 6, 14);
        System.out.println(alonzoBirthday);
        // Uses the MonthOfYear enumeration
        alonzoBirthday = LocalDate.of(1903, Month.JANUARY, 14);
        System.out.println(alonzoBirthday);
        LocalDate programmersDay = LocalDate.of(2014, 1, 1).plusDays(255);
        System.out.println(programmersDay);

        // 产生两个本地日期的时长 (Period)
        System.out.println(programmersDay.until(LocalDate.of(2014, 9, 14)).getDays());
        // 同样的功能
        System.out.println(programmersDay.plus(Period.ofDays(10)));
        System.out.println(programmersDay.plusDays(10));

        System.out.println(ChronoUnit.DAYS.getDuration().toDays());

    }

    @Test
    public void week() {
        // 周六加3天=周二
        // 与Calendar不同的是前者周日是7 而后者周日是1
        System.out.println(DayOfWeek.SATURDAY.plus(3));

        System.out.println(LocalDate.of(2022, 8, 4).getDayOfWeek().getValue());

        // JDK9 datesUntil() 方法会产生 LocalDate 对象流
        LocalDate start = LocalDate.of(2000, 1, 1);
        LocalDate exclusiveEnd = LocalDate.now();
        Stream<LocalDate> allDays = start.datesUntil(exclusiveEnd);
        Stream<LocalDate> firstDaysInMonth = start.datesUntil(exclusiveEnd, Period.ofMonths(1));
        System.out.println(allDays.findFirst());
        System.out.println(firstDaysInMonth.findFirst());
    }
}
