package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.util.List;

public class Program {

	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASSWORD = "postgres";

	public static void main(String[] args) {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(URL);
		config.setUsername(USER);
		config.setPassword(PASSWORD);

		DataSource dataSource = new HikariDataSource(config);

		UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);

		List<User> userList = usersRepository.findAll(1, 2);

		for (User user : userList) {
			System.out.println("{");
			System.out.printf("\tid = %d,\n" +
							"\tlogin = %s,\n" +
							"\tpassword = %s,\n",
					user.getId(),
					user.getLogin(),
					user.getPassword());
			System.out.print("\tcreatedRooms = {\n");
			for (Chatroom room : user.getCreatedRooms()) {
				System.out.printf("\t\tid = %d, name = %s\n", room.getId(), room.getName());
			}
			System.out.println("\t}");

			System.out.print("\tsignedRooms = {\n");
			for (Chatroom room : user.getSignedRooms()) {
				System.out.printf("\t\tid = %d, name = %s\n", room.getId(), room.getName());
			}
			System.out.println("\t}");

			System.out.println("}");
		}

	}
}
