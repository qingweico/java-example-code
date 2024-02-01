package oak.date;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * 一个关于Date类型使用equals比较引起的无限循环问题
 *
 * @author zqw
 * @date 2023/10/17
 */
@Slf4j
public class DateProblemTest {

    private static final String SD = "2023-10-16";
    private static final String ED = "2023-10-18";

    public static void main(String[] args) {
        int loop = 100;
        for (int i = 0; i < loop; i++) {
            Date startDate = parse(SD);
            Date endDate = parse(ED);
            Date terminationDate = dateAddOne(endDate);
            // 使用 下面的parse 方法解析时间时, 不会舍弃毫秒
            // 使得两个字面量相等的字符换使用 equals 比较不会相等(equals方法比较时间戳), 导致无限循环
            // 使用 compareTo 方法或者使用标准的格式化方式(Formatter)
            while (startDate.compareTo(terminationDate) < 0) {
                System.out.println(i);
                startDate = dateAddOne(startDate);
            }


        }
    }

    /**
     * 日期加一天
     *
     * @param date Date 日期
     * @return {Date + 1day}
     */
    private static Date dateAddOne(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(java.util.Calendar.DATE, 1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 字符串转日期
     *
     * @param dateString yyyy-MM-dd, yyyy/MM/dd, yyyy.MM.dd
     * @return yyyy-MM-dd
     */
    public static Date parse(String dateString) {
        StringTokenizer s = new StringTokenizer(dateString, "-/.");
        int splitStrSize = 3;
        if (s.countTokens() == splitStrSize) {
            int year = Integer.parseInt(s.nextToken());
            int month = Integer.parseInt(s.nextToken()) - 1;
            int day = Integer.parseInt(s.nextToken());

            Calendar c = Calendar.getInstance();
            log.info("now date : {}, now millisecond : {}", c.getTime(), c.get(Calendar.MILLISECOND));
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);;
            log.info("{} parse millisecond >>>> {}", dateString, c.get(Calendar.MILLISECOND));
            return c.getTime();
        }
        return new Date();
    }


    @Test
    public void calendar() {
        Date first = parse(SD);
        Date second = parse(SD);
        // 毫秒数不一样
        System.out.println(first.getTime());
        System.out.println(second.getTime());
    }
}
