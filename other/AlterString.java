package other;

import java.lang.reflect.Field;

/**
 * --------------- 反射修改字符串 ---------------
 *
 * @author zqw
 * @date 2022/7/10
 */
class AlterString {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String s = "a";
        Field field = s.getClass().getDeclaredField("value");
        // 因为 String 类中 value 字段是 private
        field.setAccessible(true);
        // TODO JDK11 报错 final
        field.set(s, "b".toCharArray());
        // b
        System.out.println(s);
    }
}
