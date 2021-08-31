package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

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

		MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

		Scanner scanner = new Scanner(System.in);

		try (Connection connection = dataSource.getConnection()){
			System.out.println("Enter a message ID");

			while (scanner.hasNext()) {
				if (!scanner.hasNextLong()) {
					System.out.println("Enter a message ID");
					continue;
				}

				long message_id = scanner.nextLong();
				Optional<Message> message = messagesRepository.findById(message_id);

				if (!message.isPresent()) {
					System.out.println("Message does not exist");
					System.out.println("Enter a message ID");
					continue;
				}

				System.out.println(message.get());
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
