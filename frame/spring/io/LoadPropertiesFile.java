package frame.spring.io;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import cn.qingweico.io.Print;

import java.io.IOException;
import java.util.Properties;

/**
 * @author zqw
 * @date 2023/12/12
 * {@link PropertiesLoaderUtils}
 * {@link PropertiesLoaderSupport }
 * {@link PropertySourcesPlaceholderConfigurer}
 */
public class LoadPropertiesFile {
    public static void main(String[] args) throws IOException {
        Resource resource = new ClassPathResource("db.properties");
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);
        Print.printMap(properties);
    }
}


class InitDbPropertiesLoader extends PropertySourcesPlaceholderConfigurer {
    public static void main(String[] args) {

    }

}
