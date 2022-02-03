package oak;

import org.junit.Test;

import java.time.*;
import java.util.Base64;

/**
 * @author zqw
 * @date 2022/10/22
 */
public class NewFeatureTest {
    @Test
    public void localDate() {
        var date = LocalDate.of(2021, 10, 22);
        var time = LocalTime.of(19, 36);
        var dateTime = LocalDateTime.of(date, time);
        System.out.println(dateTime);
        var zoneDt = ZonedDateTime.of(dateTime, ZoneId.of("Europe/Paris"));
        System.out.println(zoneDt);
    }

    @Test
    public void base64() {
        Base64.Decoder decode = Base64.getDecoder();
        Base64.Encoder encoder = Base64.getEncoder();
        String encoderString = encoder.encodeToString(new byte[]{'b', 'a', 's', 'e', '6', '4'});
        System.out.println(encoderString);
        String decodeString = new String(decode.decode(encoderString));
        System.out.println(decodeString);
    }
}
