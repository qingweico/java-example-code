package frame.db;

import com.zaxxer.hikari.HikariDataSource;
import object.enums.DbConProperty;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import util.DatabaseHelper;

import java.util.List;
import java.util.Properties;

/**
 * @author zqw
 * @date 2023/2/18
 */
public class JdbcConfig {

    public JdbcTemplate createTemplate() {
        HikariDataSource hds = new HikariDataSource();
        Properties properties = DatabaseHelper.loadDbConfig();
        hds.setDriverClassName(properties.getProperty(DbConProperty.DRIVE_CLASS_NAME.getProperty()));
        hds.setJdbcUrl(properties.getProperty(DbConProperty.JDBC_URL.getProperty()));
        hds.setUsername(properties.getProperty(DbConProperty.USERNAME.getProperty()));
        hds.setPassword(properties.getProperty(DbConProperty.PASSWORD.getProperty()));
        return new JdbcTemplate(hds) {
            @Override
            @SuppressWarnings("deprecation")
            public <T> T queryForObject(@NotNull String sql, @Nullable Object[] args, @NotNull RowMapper<T> rowMapper) throws DataAccessException {
                List<T> results = query(sql, args, new RowMapperResultSetExtractor<>(rowMapper, 1));
                if (CollectionUtils.isEmpty(results)) {
                    return null;
                }
                return DataAccessUtils.nullableSingleResult(results);
            }
        };
    }
}
