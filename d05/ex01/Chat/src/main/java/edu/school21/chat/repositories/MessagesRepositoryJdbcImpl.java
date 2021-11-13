package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

	private final DataSource dataSource;

	public MessagesRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Optional<Message> findById(Long id) {
		try (Connection connection = dataSource.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet;

			resultSet = statement.executeQuery(String.format("SELECT * FROM t_messages WHERE f_id = '%d';", id));

			if (!resultSet.next()) {
				return Optional.empty();
			}

			long authorId = resultSet.getLong("f_author_id");
			long chatroomId = resultSet.getLong("f_chatroom_id");
			String text = resultSet.getString("f_text");
			Timestamp dateTime = resultSet.getTimestamp("f_datetime");
			resultSet.close();

			resultSet = statement.executeQuery(String.format("SELECT * FROM t_users WHERE f_id = '%d';", authorId));

			if (!resultSet.next()) {
				return Optional.empty();
			}

			String login = resultSet.getString("f_login");
			String password = resultSet.getString("f_password");
			resultSet.close();

			resultSet = statement.executeQuery(String.format("SELECT * FROM t_chatrooms WHERE f_id = '%d';", chatroomId));

			if (!resultSet.next()) {
				return Optional.empty();
			}

			String roomName = resultSet.getString("f_name");

			statement.close();

			User user = new User(authorId, login, password);
			Chatroom chatroom = new Chatroom(chatroomId, roomName, user);
			Message message = new Message(id, user, chatroom, text, dateTime);

			return Optional.of(message);
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
}
