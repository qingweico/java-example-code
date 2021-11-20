package eight;

import java.time.*;

/**
 * @author:qiming
 * @date: 2021/10/22
 */
public class LocalD {
    public static void main(String[] args) {
        var date = LocalDate.of(2021, 10, 22);
        var time = LocalTime.of(19, 36);
        var dateTime = LocalDateTime.of(date, time);
        System.out.println(dateTime);
        var zoneDT = ZonedDateTime.of(dateTime, ZoneId.of("Europe/Paris"));
        System.out.println(zoneDT);
    }
}
