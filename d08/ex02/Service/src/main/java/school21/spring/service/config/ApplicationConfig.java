package school21.spring.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

@Configuration
@ComponentScan("school21.spring.service")
@PropertySource("classpath:/db.properties")
public class ApplicationConfig {

	@Autowired
	private Environment environment;

	@Bean
	public HikariConfig hikariConfig() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(environment.getProperty("db.url"));
		hikariConfig.setUsername(environment.getProperty("db.user"));
		hikariConfig.setPassword(environment.getProperty("db.password"));
		hikariConfig.setUsername(environment.getProperty("db.driver.name"));
		return hikariConfig;
	}

	@Bean
	public HikariDataSource hikariDataSource(HikariConfig hikariConfig) {
		return new HikariDataSource(hikariConfig);
	}

	@Bean
	public UsersRepository usersRepositoryJdbc() {
		return new UsersRepositoryJdbcImpl();
	}

	@Bean
	public UsersRepository usersRepositoryJdbcTemplate() {
		return new UsersRepositoryJdbcTemplateImpl();
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(HikariDataSource hikariDataSource) {
		return new NamedParameterJdbcTemplate(hikariDataSource);
	}
}
