package util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
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

    public static Connection getConnection() {
        properties = new Properties();
        FileInputStream fin;
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
