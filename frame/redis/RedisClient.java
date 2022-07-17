package frame.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.Properties;;

/**
 * @author zqw
 * @date 2022/7/13
 */
public class RedisClient {

    private static final JedisPool jedisPool;

    static {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream("redis.properties"));
            Properties properties = new Properties();
            properties.load(is);
            String host = properties.getProperty("host");
            Integer timeout = Integer.parseInt(properties.getProperty("timeout"));
            String password = properties.getProperty("password");
            Integer port = Integer.parseInt(properties.getProperty("port"));
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("max_total")));
            jedisPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty("max_idle")));
            jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(properties.getProperty("max_wait")));
            jedisPoolConfig.setTestOnBorrow(Boolean.getBoolean(properties.getProperty("test_on_borrow")));
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static Jedis getJedis() {
        return jedisPool.getResource();
    }
}
