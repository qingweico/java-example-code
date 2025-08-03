package misc.spi;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zqw
 * @date 2025/7/23
 */
@Slf4j
public class SqlServerDriver implements DatabaseDriver {
    @Override
    public void connect(String url) {
        log.info("SqlServer connected to {}", url);
    }

    @Override
    public void disconnect() {
        log.info("SqlServer disconnect");
    }
    @Override
    public String toString() {
        return "SqlServerDriver";
    }

}
