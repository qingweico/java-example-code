package util.constants;

/**
 * Path Constants Definition
 *
 * @author zqw
 */
public interface PathConstants {

    /**
     * Slash : <code>"/"</code>
     */
    String SLASH = "/";
    /**
     * Double Slash : <code>"//"</code>
     */
    String DOUBLE_SLASH = SLASH + SLASH;
    /**
     * Backslash : <code>"\"</code>
     */
    String BACK_SLASH = "\\";

    /**
     * 数据库配置文件路径
     */
    String DB_CONFIG_FILE_PATH = "db.properties";

    /**
     * Redis配置文件路径
     */
    String REDIS_CONFIG_FILE_PATH = "redis.properties";
}
