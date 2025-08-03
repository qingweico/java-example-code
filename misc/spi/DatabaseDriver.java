package misc.spi;

/**
 * @author zqw
 * @date 2025/7/23
 */
public interface DatabaseDriver {
    void connect(String url);
    void disconnect();
}
