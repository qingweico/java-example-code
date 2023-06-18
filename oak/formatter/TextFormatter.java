package oak.formatter;

import cn.hutool.core.util.StrUtil;
import org.slf4j.helpers.MessageFormatter;

import java.util.HashMap;

/**
 * @author zqw
 * @date 2023/6/17
 */
class TextFormatter {
    public static void main(String[] args) {

        // slf4j
        System.out.println(MessageFormatter.format("Hello {}", "World").getMessage());

        System.out.println(MessageFormatter.format("Hello \\{}", "World").getMessage());

        HashMap<String, Object> map = new HashMap<>(4);
        map.put("msg", "no data");
        map.put("code", "200");
        System.out.println(StrUtil.format("msg = {msg}, code = {code}", map));
    }
}
