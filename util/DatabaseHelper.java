package util;

import lombok.extern.slf4j.Slf4j;
import object.enums.DbConProperty;
import thread.aqs.ObjectPool;
import util.constants.PathConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * @author zqw
 * @date 2022/6/25
 */
@Slf4j
public class DatabaseHelper {

    /*these field value can be revised before using*/

    static String driveClassName;
    static String dbUlr;
    static String username;
    static String password;
    static String db = PathConstants.DB_CONFIG_FILE_PATH;

    private static final int DEFAULT_POOL_SIZE = 10;

    static {
        Properties properties = loadDbConfig();
        FileInputStream fin;
        // 设置 JDBC 日志流到控制台
        DriverManager.setLogWriter(new PrintWriter(new PrintStream(System.out), true, Charset.defaultCharset()));
        try {
            fin = new FileInputStream(db);
            properties.load(fin);
        } catch (IOException e) {
            log.error("load {} error, {}", db, e.getMessage());
        }
        driveClassName = properties.getProperty(DbConProperty.DRIVE_CLASS_NAME.getProperty());
        dbUlr = properties.getProperty(DbConProperty.JDBC_URL.getProperty());
        username = properties.getProperty(DbConProperty.USERNAME.getProperty());
        password = properties.getProperty(DbConProperty.PASSWORD.getProperty());
    }

    public static Properties loadDbConfig() {
        Properties properties = new Properties();
        FileInputStream fin;
        // 设置 JDBC 日志流到控制台
        DriverManager.setLogWriter(new PrintWriter(new PrintStream(System.out), true, Charset.defaultCharset()));
        try {
            fin = new FileInputStream(db);
            properties.load(fin);
        } catch (IOException e) {
            log.error("load {} error, {}", db, e.getMessage());
        }
        return properties;
    }

    public static Connection getConnection() {
        try {
            Class.forName(driveClassName);
        } catch (ClassNotFoundException e) {
            log.error("drive class not found, {}", e.getMessage());
        }
        try {
            return DriverManager.getConnection(dbUlr, username, password);
        } catch (SQLException e) {
            log.error("get connection error, {}", e.getMessage());
        }
        return null;
    }

    public static ObjectPool<Connection, Object> getPool() {
        return getPool(DEFAULT_POOL_SIZE);
    }

    public static ObjectPool<Connection, Object> getPool(int poolSize) {
        Connection connection = getConnection();
        return new ObjectPool<>(poolSize, connection);
    }
}
