package util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author zqw
 * @date 2021/4/7
 * @see org.apache.commons.lang3.time.DateUtils
 */
public class DateUtil {

    private static final String DEFAULT_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    private static final ThreadLocal<DateFormat> TL = ThreadLocal.withInitial(() -> new SimpleDateFormat(DEFAULT_FORMATTER));
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_FORMATTER);

    public static LocalDateTime parse(String dateStr) {
        return LocalDateTime.parse(dateStr, DEFAULT_DATE_TIME_FORMATTER);
    }

    public static String format() {
        return format(DEFAULT_FORMATTER);
    }

    public static String format(String pattern) {
        return FastDateFormat.getInstance(pattern).format(new Date());
    }

    public static String format(Date date) {
        return FastDateFormat.getInstance(DEFAULT_FORMATTER).format(date);
    }

    public static String format(Date date, String pattern) {
        return FastDateFormat.getInstance(pattern).format(date);
    }

    public static String tlFormat() {
        try {
            DateFormat dateFormat = TL.get();
            return dateFormat.format(new Date());
        } finally {
            TL.remove();
        }
    }

    public static long nowTt() {
        return System.currentTimeMillis();
    }

    public static String now() {
        return format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(parse("1999-01-01 00:00:00"));
        System.out.println(tlFormat());
        System.out.println(nowTt());
    }
}
