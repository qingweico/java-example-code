package util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author zqw
 * @date 2021/4/7
 */
public class DateUtil {

    private static final String DEFAULT_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat SDF;
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(DEFAULT_FORMATTER);

    public static LocalDateTime parse(String dateStr) {
        return LocalDateTime.parse(dateStr, DEFAULT_DATE_TIME_FORMATTER);
    }

    public static String format() {
        return format(DEFAULT_FORMATTER);
    }

    public static String format(String pattern) {
        return FastDateFormat.getInstance(pattern).format(new Date());
    }

}
