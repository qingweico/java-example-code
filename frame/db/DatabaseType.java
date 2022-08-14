package frame.db;

import lombok.extern.slf4j.Slf4j;
import util.DatabaseHelper;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zqw
 * @date 2022/6/25
 */
@Slf4j
public class DatabaseType {
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        Connection connection = DatabaseHelper.getConnection();
        if (connection != null) {
            log.info("database type: {}", connection.getMetaData().getDatabaseProductName());
        } else {
            log.error("connection is null");
        }
    }
}
