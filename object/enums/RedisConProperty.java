package object.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Redis连接属性
 *
 * @author zqw
 * @date 2023/2/18
 */
@AllArgsConstructor
@Getter
public enum RedisConProperty {
    /**
     * database
     */
    DATABASE("database"),
    /**
     * host
     */
    HOST("host"),
    /**
     * max_idle
     */
    MAX_IDLE("max_idle"),
    /**
     * max_total
     */
    MAX_TOTAL("max_total"),
    /**
     * max_wait
     */
    MAX_WAIT("max_wait"),
    /**
     * password
     */
    PASSWORD("password"),
    /**
     * port
     */
    PORT("port"),
    /**
     * test_on_borrow
     */
    TEST_ON_BORROW("test_on_borrow"),
    /**
     * timeout
     */
    TIMEOUT("timeout");

    private final String property;
}
