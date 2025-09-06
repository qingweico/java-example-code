package awt;

import cn.qingweico.supplier.RandomDataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.ThreadUtils;

import java.awt.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author zqw
 * @date 2025/9/5
 */
@Slf4j
class PreventScreenLocking {
    public static void main(String[] args) {
        for (; ; ) {
            mouseMove(null, null);
            try {
                Duration duration = Duration.of(15, ChronoUnit.MINUTES);
                ThreadUtils.sleep(duration);
            } catch (InterruptedException ignored) {

            }
        }
    }

    @SuppressWarnings("unused")
    public static void pressSingleKeyByNumber(int keycode) {
        try {
            Robot robot = new Robot();
            robot.keyPress(keycode);
            robot.keyRelease(keycode);
            robot.delay(500);
        } catch (AWTException ignored) {
        }

    }


    public static void mouseMove(Integer x, Integer y) {
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Robot robot = new Robot();
            Dimension screenSize = toolkit.getScreenSize();
            int screenWidth = screenSize.width;
            int screenHeight = screenSize.height;


            if (x == null) {
                x = RandomDataGenerator.rndInt(screenWidth);
            }

            if (y == null) {
                y = RandomDataGenerator.rndInt(screenHeight);
            }
            log.info("move X : {}, Y : {}", x, y);
            robot.mouseMove(x, y);
            robot.delay(100);
        } catch (AWTException ignored) {

        }

    }
}
