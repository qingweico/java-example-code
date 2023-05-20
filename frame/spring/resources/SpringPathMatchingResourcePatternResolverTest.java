package frame.spring.resources;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * @author zqw
 * @date 2023/5/20
 * @see org.springframework.core.io.support.PathMatchingResourcePatternResolver
 */
@Slf4j
public class SpringPathMatchingResourcePatternResolverTest {


    @Test
    public void readFile()  {
        System.out.println(read("classpath:object/oop/Father.class"));
    }

    /**
     * 读取指定路径下(file jar url .etc)的内容并输出为String
     * @param path eg: classpath:META-INF/spring.factories
     * @return String
     */
    public static String read(String path) {
        ResourcePatternResolver ppr = new PathMatchingResourcePatternResolver();
        Resource resource = ppr.getResource(path);
        log.info("desc: {}", resource.getDescription());
        try {
            byte[] bytes = IOUtils.toByteArray(resource.getInputStream());
            return new String(bytes);
        }catch (IOException e) {
            return null;
        }
    }

    @Test
    public void readNetwork() {
        System.out.println(read("https://www.qingweico.cn"));

    }
}
