package misc;

import java.lang.reflect.Field;

/**
 * --------------- 反射修改字符串 ---------------
 *
 * @author zqw
 * @date 2022/7/10
 */
class AlterString {
    public static void main(String[] args) throws Exception {
        var str = new Object() {
            // remove final
            final String value = "a";
        };
        Field field = str.getClass().getDeclaredField("value");
        field.setAccessible(true);
        field.set(str, "b");
        // b
        System.out.println(str.value);
    }
}