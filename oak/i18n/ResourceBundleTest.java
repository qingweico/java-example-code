package oak.i18n;

import cn.qingweico.utils.ResourceBundleUtil;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * @author zqw
 * @date 2025/8/31
 */
public class ResourceBundleTest {
    @Test
    public void load() throws IOException {
        Resource resource = new ClassPathResource("messages_zh_CN.properties");
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);
        for (Object key : properties.keySet()) {
            System.out.println(ResourceBundleUtil.getMessage((String) key, Locale.SIMPLIFIED_CHINESE));
        }
    }

}
