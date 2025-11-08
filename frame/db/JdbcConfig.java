package frame.db;

import cn.qingweico.database.DatabaseHelper;
import cn.qingweico.model.enums.DbConProperty;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class JdbcConfig extends AbstractTmplConfig implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Bean
    public HikariDataSource hikariDataSource() {
        HikariDataSource hds = new HikariDataSource();
        Properties properties = DatabaseHelper.loadDbConfig();
        hds.setDriverClassName(properties.getProperty(DbConProperty.DRIVE_CLASS_NAME.getProperty()));
        hds.setJdbcUrl(properties.getProperty(DbConProperty.JDBC_URL.getProperty()));
        hds.setUsername(properties.getProperty(DbConProperty.USERNAME.getProperty()));
        hds.setPassword(properties.getProperty(DbConProperty.PASSWORD.getProperty()));
        return hds;
    }


    @Override
    public DataSource getDataSource() {
        return applicationContext.getBean(HikariDataSource.class);
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
