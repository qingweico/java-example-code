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
    DATABASE("database"), HOST("host"), PORT("port"), PASSWORD("password"),TIMEOUT("timeout"),
    MAX_TOTAL("max_total"), TEST_ON_BORROW("test_on_borrow"), MAX_WAIT("max_wait"),MAX_IDLE("max_idle");

    private final String property;
}
