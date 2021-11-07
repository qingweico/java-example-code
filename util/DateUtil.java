package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author:qiming
 * @date: 2021/4/7
 */
public class DateUtil {
    private static final DateTimeFormatter DEFAULT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parse(String dateStr) {
        return LocalDateTime.parse(dateStr, DEFAULT_FORMATTER);
    }
}
