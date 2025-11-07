package frame.db;

import cn.qingweico.database.DatabaseHelper;
import cn.qingweico.database.NamedSqlTmplQuery;
import cn.qingweico.database.SqlTmplQuery;
import cn.qingweico.model.enums.DbConProperty;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
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


    /**
     * @see SqlTmplQuery
     */
    @Bean
    public JdbcTemplate createTemplate(DataSource hds) {
        return new JdbcTemplate(hds);
    }
    /**
     * @see NamedSqlTmplQuery
     */
    @Bean
    public NamedParameterJdbcTemplate createNamedParameterJdbcTemplate(DataSource hds) {
        return new NamedParameterJdbcTemplate(hds);
    }
}
