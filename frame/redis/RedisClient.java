package frame.redis;

import lombok.extern.slf4j.Slf4j;
import object.enums.RedisConProperty;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import util.constants.PathConstants;

import java.io.*;
import java.util.Properties;

/**
 * @author zqw
 * @date 2022/7/13
 */
@Slf4j
public class RedisClient {

    private static final JedisPool JEDIS_POOL;

    static {
        Properties properties = loadRedisConfig();
        String host = properties.getProperty(RedisConProperty.HOST.getProperty());
        Integer database = Integer.parseInt(properties.getProperty(RedisConProperty.DATABASE.getProperty()));
        Integer timeout = Integer.parseInt(properties.getProperty(RedisConProperty.TIMEOUT.getProperty()));
        String password = properties.getProperty(RedisConProperty.PASSWORD.getProperty());
        if (StringUtils.isEmpty(password)) {
            password = null;
        }
        Integer port = Integer.parseInt(properties.getProperty(RedisConProperty.PORT.getProperty()));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty(RedisConProperty.MAX_TOTAL.getProperty())));
        jedisPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty(RedisConProperty.MAX_IDLE.getProperty())));
        jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(properties.getProperty(RedisConProperty.MAX_WAIT.getProperty())));
        jedisPoolConfig.setTestOnBorrow(Boolean.getBoolean(properties.getProperty(RedisConProperty.TEST_ON_BORROW.getProperty())));
        JEDIS_POOL = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
    }

    public static Properties loadRedisConfig() {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(PathConstants.REDIS_CONFIG_FILE_PATH));
            Properties properties = new Properties();
            properties.load(is);
            log.info("RedisClient Configuration : {} ", properties);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public synchronized static Jedis getJedis() {
        return JEDIS_POOL.getResource();
    }
}
