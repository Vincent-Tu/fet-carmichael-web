package fet.carmichael.config;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author 
 *
 */
@Configuration
public class JDBCConfiguration {

	private JdbcTemplate jdbcTemplateForH2;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplateForH2;
	@Value("${h2.datasource.url}")
	String h2Url;
	@Value("${h2.datasource.username}")
	private String h2Username;
	@Value("${h2.datasource.password}")
	private String h2Password;

	@Bean(name = "jdbcTemplateForH2")
	public JdbcTemplate getJdbcTemplateForH2() {
		return jdbcTemplateForH2;
	}

	@Bean(name = "namedParameterJdbcTemplateForH2")
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplateForH2() {
		return namedParameterJdbcTemplateForH2;
	}

	@Autowired
	@Qualifier("dataSourceForH2")
	public void setJdbcTemplateForH2(DataSource dataSourceForH2) {
		this.jdbcTemplateForH2 = new JdbcTemplate(dataSourceForH2);
		this.namedParameterJdbcTemplateForH2 = new NamedParameterJdbcTemplate(dataSourceForH2);
	}

	@Primary
	@Bean(name = "dataSourceForH2")
	public DataSource dataSourceForH2() {
		final JdbcDataSource jdbcDataSource = new JdbcDataSource();
		jdbcDataSource.setURL(h2Url);
		jdbcDataSource.setUser(h2Username);
		jdbcDataSource.setPassword(h2Password);

		return jdbcDataSource;
	}
}
