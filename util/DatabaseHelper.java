package util;

import lombok.extern.slf4j.Slf4j;

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

    static Properties properties;
    static String driveClassName;
    static String dbUlr;
    static String username;
    static String password;

    static {
        properties = new Properties();
        FileInputStream fin;
        // 设置 JDBC 日志流到控制台
        DriverManager.setLogWriter(new PrintWriter(new PrintStream(System.out), true, Charset.defaultCharset()));
        try {
            fin = new FileInputStream("db.properties");
            properties.load(fin);
        } catch (IOException e) {
            log.error("io error");
        }
        driveClassName = properties.getProperty("driver");
        dbUlr = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");

    }

    public static Connection getConnection() {
        try {
            Class.forName(driveClassName);
        } catch (ClassNotFoundException e) {
            log.error("drive class not found");
        }
        try {
            return DriverManager.getConnection(dbUlr, username, password);
        } catch (SQLException e) {
            log.error("sql connection error");
        }
        return null;
    }
}
