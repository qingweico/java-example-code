package frame.redis;

import cn.qingweico.model.enums.RedisConProperty;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import cn.qingweico.constants.Constants;
import cn.qingweico.constants.PathConstants;
import cn.qingweico.constants.Symbol;

import java.util.Properties;

/**
 * @author zqw
 * @date 2023/2/18
 */
public class RedissonConfig {
    private static final String HOST;
    private static final String PASSWORD;
    private static final Integer PORT;
    private static final Integer DATABASE;


    static {
        Properties properties = RedisClient.loadRedisConfig();
        HOST = properties.getProperty(RedisConProperty.HOST.getProperty());
        DATABASE = Integer.parseInt(properties.getProperty(RedisConProperty.DATABASE.getProperty()));
        PASSWORD = properties.getProperty(RedisConProperty.PASSWORD.getProperty());
        PORT = Integer.parseInt(properties.getProperty(RedisConProperty.PORT.getProperty()));
    }


    public RedissonClient redisson() {
        Config config = new Config();
        String address = Constants.REDIS + Symbol.COLON + PathConstants.DOUBLE_SLASH + HOST + Symbol.COLON + PORT;
        config.useSingleServer().setAddress(address);
        config.useSingleServer().setDatabase(DATABASE);
        if (StringUtils.isNotBlank((PASSWORD))) {
            config.useSingleServer().setPassword(PASSWORD);
        }
        return Redisson.create(config);
    }
}
