package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

		try (Connection connection = dataSource.getConnection()){
			Optional<Message> messageOptional = messagesRepository.findById(1L);

			if (messageOptional.isPresent()) {
				Message message = messageOptional.get();
				System.out.println(message);
				message.setText("pam-pam");
				message.setDateTime(null);
				messagesRepository.update(message);
				System.out.println(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
