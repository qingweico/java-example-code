package frame.db;

import cn.qingweico.database.DatabaseHelper;
import cn.qingweico.model.enums.DbConProperty;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

/**
 * @author zqw
 * @date 2023/2/18
 * @see JdbcTemplate
 * @see NamedParameterJdbcTemplate
 */
@Configuration
public class JdbcConfig {

    @Bean
    public DataSource dataSource() {
        HikariDataSource hds = new HikariDataSource();
        Properties properties = DatabaseHelper.loadDbConfig();
        hds.setDriverClassName(properties.getProperty(DbConProperty.DRIVE_CLASS_NAME.getProperty()));
        hds.setJdbcUrl(properties.getProperty(DbConProperty.JDBC_URL.getProperty()));
        hds.setUsername(properties.getProperty(DbConProperty.USERNAME.getProperty()));
        hds.setPassword(properties.getProperty(DbConProperty.PASSWORD.getProperty()));
        return hds;
    }


    @Bean
    public JdbcTemplate createTemplate(DataSource hds) {
        return new JdbcTemplate(hds) {
            @Override
            public <T> T queryForObject(@NotNull String sql, @NotNull RowMapper<T> rowMapper, @Nullable Object... args) throws DataAccessException {
                List<T> results = query(sql, new RowMapperResultSetExtractor<>(rowMapper, 1), args);
                if (CollectionUtils.isEmpty(results)) {
                    return null;
                }
                return DataAccessUtils.nullableSingleResult(results);
            }
        };
    }

    @Bean
    public NamedParameterJdbcTemplate createNamedParameterJdbcTemplate(DataSource hds) {
        return new NamedParameterJdbcTemplate(hds) {
            @Override
            public <T> T queryForObject(@NotNull String sql, @NotNull SqlParameterSource paramSource, @NotNull RowMapper<T> rowMapper) {
                List<T> results = query(sql, paramSource, rowMapper);
                if (CollectionUtils.isEmpty(results)) {
                    return null;
                }
                return DataAccessUtils.nullableSingleResult(results);
            }
        };
    }
}
