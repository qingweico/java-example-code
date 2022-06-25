package frame.database;

import lombok.extern.slf4j.Slf4j;
import util.DatabaseHelper;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zqw
 * @date 2022/6/25
 */
@Slf4j
public class DatabaseType {
    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseHelper.getConnection();
        if (connection != null) {
            log.info("database type: {}", connection.getMetaData().getDatabaseProductName());
        } else {
            log.error("connection is null");
        }
    }
}
