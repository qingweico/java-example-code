package oak.i18n;

import cn.qingweico.datetime.DateUtil;
import cn.qingweico.utils.ResourceBundleUtil;

import java.util.Locale;

/**
 * @author zqw
 * @date 2025/8/31
 */
public class ParameterizedMessages {
    public static void main(String[] args) {
        String message = ResourceBundleUtil.getMessage("Size.message", Locale.SIMPLIFIED_CHINESE, 6, 10);
        System.out.println(message);
        // 文件命名与英文locale的关系
        // messages_en_US.properties -> Locale.US -> 美国英语
        // messages_en_GB.properties -> Locale.UK -> 英国英语
        // messages_en.properties -> Locale.ENGLISH -> 通用英语
        message = ResourceBundleUtil.getMessage("login.time", Locale.US, DateUtil.now());
        System.out.println(message);
    }
}
