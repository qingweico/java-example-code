package object.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据据连接属性
 *
 * @author zqw
 * @date 2023/2/18
 */
@AllArgsConstructor
@Getter
public enum DbConProperty {

    /**
     * drive_class_name
     */
    DRIVE_CLASS_NAME("driver"),
    /**
     * jdbc_url
     */
    JDBC_URL("url"),
    /**
     * username
     */
    USERNAME("username"),
    /**
     * password
     */
    PASSWORD("password");

    private final String property;
}
