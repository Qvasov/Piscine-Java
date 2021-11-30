package edu.school21.sockets.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.sockets.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan("edu.school21.sockets")
@PropertySource("classpath:/db.properties")
public class SocketsApplicationConfig {

	@Autowired
	Environment environment;

	@Bean
	public HikariConfig hikariConfig() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(environment.getProperty("db.url"));
		hikariConfig.setUsername(environment.getProperty("db.username"));
		hikariConfig.setPassword(environment.getProperty("db.password"));
		hikariConfig.setDriverClassName(environment.getProperty("db.driver.name"));
		return hikariConfig;
	}

	@Bean
	public HikariDataSource hikariDataSource(HikariConfig hikariConfig) {
		return new HikariDataSource(hikariConfig);
	}

	@Bean
	public UsersRepository usersRepository() {
		return new UsersRepositoryImpl();
	}

	@Bean
	public RoomsRepository roomsRepository() {
		return new RoomsRepositoryImpl();
	}

	@Bean
	public MessagesRepository messagesRepository() {
		return new MessagesRepositoryImpl();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(HikariDataSource hikariDataSource) {
		return new NamedParameterJdbcTemplate(hikariDataSource);
	}
}
