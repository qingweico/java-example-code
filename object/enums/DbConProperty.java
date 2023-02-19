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

    DRIVE_CLASS_NAME("driver"), JDBC_URL("url"), USERNAME("username"), PASSWORD("password");

    private final String property;
}
