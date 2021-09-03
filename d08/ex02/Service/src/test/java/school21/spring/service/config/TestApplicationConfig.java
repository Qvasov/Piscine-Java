package school21.spring.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;

import javax.sql.DataSource;

@Configuration
public
class TestApplicationConfig {

	@Bean
	public DataSource embeddedDatabase() {
		EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
		return databaseBuilder
				.generateUniqueName(true)
				.setType(EmbeddedDatabaseType.HSQL)
				.ignoreFailedDrops(true)
				.setScriptEncoding("UTF-8")
				.addScript("schema.sql")
				.build();
	}

	@Bean
	public UsersRepository usersRepositoryJdbc(DataSource dataSource) {
		return new UsersRepositoryJdbcImpl(dataSource);
	}

	@Bean
	public UsersRepository usersRepositoryJdbcTemplate(DataSource dataSource) {
		return new UsersRepositoryJdbcTemplateImpl(dataSource);
	}

	@Bean
	public UsersServiceImpl usersService() {
		return new UsersServiceImpl();
	}
}