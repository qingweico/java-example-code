package frame.db;

import cn.qingweico.database.NamedSqlTmplQuery;
import cn.qingweico.database.SqlTmplQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * @author zqw
 * @date 2025/11/8
 */
public abstract class AbstractTmplConfig {
    /**
     * @see SqlTmplQuery
     */
    @Bean
    protected JdbcTemplate createTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    /**
     * @see NamedSqlTmplQuery
     */
    @Bean
    protected NamedParameterJdbcTemplate createNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(getDataSource());
    }

    public abstract DataSource getDataSource();
}
