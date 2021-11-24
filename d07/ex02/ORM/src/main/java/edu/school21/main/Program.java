package edu.school21.main;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.entities.User;
import edu.school21.managers.OrmManager;

public class Program {
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASSWORD = "postgres";

	public static void main(String[] args) {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(URL);
		config.setUsername(USER);
		config.setPassword(PASSWORD);

		OrmManager ormManager = new OrmManager(new HikariDataSource(config));
		ormManager.save(new User("fn", "ln", 20));
		User user = ormManager.findById(1L, User.class);
		user.setFirstName("first_name");
		user.setLastName(null);
		user.setAge(10);
		ormManager.update(user);
	}
}
