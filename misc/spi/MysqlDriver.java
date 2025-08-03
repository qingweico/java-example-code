package misc.spi;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zqw
 * @date 2025/7/23
 */
@Slf4j
public class MysqlDriver implements DatabaseDriver {
    @Override
    public void connect(String url) {
        log.info("MySQL connected to {}", url);
    }

    @Override
    public void disconnect() {
        log.info("MySQL disconnect");
    }

    @Override
    public String toString() {
        return "MysqlDriver";
    }
}
