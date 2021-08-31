package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

		MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

		try (Connection connection = dataSource.getConnection()){
			List<User> userList = new ArrayList<>();
			List<Chatroom> chatroomList = new ArrayList<>();
			Statement statement = connection.createStatement();
			ResultSet resultSet;

			resultSet = statement.executeQuery("SELECT * FROM t_users;");
			while (resultSet.next()) {
				userList.add(
						new User(resultSet.getLong("f_id"),
								resultSet.getString("f_login"),
								resultSet.getString("f_password"))
				);
			}

			resultSet.close();
			resultSet = statement.executeQuery("SELECT * FROM t_chatrooms;");
			while (resultSet.next()) {
				User owner = null;

				for (User u : userList) {
					if (u.getId().equals(resultSet.getLong("f_owner_id"))) {
						owner = u;
					}
				}

				chatroomList.add(
						new Chatroom(resultSet.getLong("f_id"),
								resultSet.getString("f_name"),
								owner)
				);
			}

			Message message;
			try {
				message = new Message(userList.get(0), chatroomList.get(6), "tutu", Timestamp.valueOf(LocalDateTime.now()));
				messagesRepository.save(message);
				System.out.println(message);
			} catch (NotSavedSubEntityException e) {
				e.printStackTrace();
			}

			User fakeUser1 = new User(999L, "java", "java");
			User fakeUser2 = new User(999L, "java", "java");
			Chatroom fakeRoom1 = new Chatroom(null, "fakeRoom", fakeUser1);
			Chatroom fakeRoom2 = new Chatroom(null, "fakeRoom", fakeUser2);

			try {
				message = new Message(fakeUser1, chatroomList.get(7), "toto1", Timestamp.valueOf(LocalDateTime.now()));
				messagesRepository.save(message);
			} catch (NotSavedSubEntityException e) {
				e.printStackTrace();
			}

			try {
				message = new Message(fakeUser2, chatroomList.get(6), "toto2", Timestamp.valueOf(LocalDateTime.now()));
				messagesRepository.save(message);
			} catch (NotSavedSubEntityException e) {
				e.printStackTrace();
			}

			try {
				message = new Message(userList.get(3), fakeRoom1, "tata1", Timestamp.valueOf(LocalDateTime.now()));
				messagesRepository.save(message);
			} catch (NotSavedSubEntityException e) {
				e.printStackTrace();
			}

			try {
				message = new Message(userList.get(4), fakeRoom2, "tata2", Timestamp.valueOf(LocalDateTime.now()));
				messagesRepository.save(message);
			} catch (NotSavedSubEntityException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
