package misc.spi;

import java.util.ServiceLoader;

/**
 * @author zqw
 * @date 2025/7/23
 */
public class SpiValidator {
    public static <T> void validate(Class<T> iService) {
        ServiceLoader.load(iService)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No implementation found for SPI: " + iService.getName()
                ));
    }
}
