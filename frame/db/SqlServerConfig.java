package frame.db;

import cn.qingweico.constants.PathConstants;
import cn.qingweico.model.enums.DbConProperty;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author zqw
 * @date 2025/11/8
 */
@Configuration
public class SqlServerConfig extends AbstractTmplConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    public SQLServerDataSource sqlServerDataSource() throws IOException {
        SQLServerDataSource sds = new SQLServerDataSource();
        Properties properties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(PathConstants.SQLSERVER_CONFIG_FILE_PATH));
        sds.setURL(properties.getProperty(DbConProperty.JDBC_URL.getProperty()));
        sds.setUser(properties.getProperty(DbConProperty.USERNAME.getProperty()));
        sds.setPassword(properties.getProperty(DbConProperty.PASSWORD.getProperty()));
        sds.setDatabaseName(properties.getProperty(DbConProperty.DATABASE.getProperty()));
        return sds;
    }

    @Override
    public DataSource getDataSource() {
        return applicationContext.getBean(SQLServerDataSource.class);
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
