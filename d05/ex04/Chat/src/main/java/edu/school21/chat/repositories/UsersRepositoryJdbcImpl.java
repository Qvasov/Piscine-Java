package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UsersRepositoryJdbcImpl implements UsersRepository {

	private final DataSource dataSource;

	public UsersRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<User> findAll(int page, int size) {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"WITH users AS (SELECT * FROM t_users WHERE f_id BETWEEN ? AND ?)," +
							"     chatrooms_signin AS (" +
							"         SELECT t_chatrooms.f_id, f_name, f_owner_id, messages.f_login, messages.f_password" +
							"         FROM t_chatrooms" +
							"             INNER JOIN (" +
							"                 SELECT t_messages.f_author_id, f_chatroom_id, users.f_login, users.f_password" +
							"                 FROM t_messages" +
							"                     INNER JOIN users" +
							"                         ON t_messages.f_author_id = users.f_id" +
							"                 ) messages" +
							"                 ON t_chatrooms.f_id = messages.f_chatroom_id)," +
							"     chatrooms_owner AS (" +
							"         SELECT t_chatrooms.f_id, f_name, f_owner_id, users.f_login, users.f_password" +
							"         FROM t_chatrooms" +
							"             INNER JOIN users" +
							"                 ON t_chatrooms.f_owner_id = users.f_id" +
							"         )" +
							"SELECT chatrooms_owner.f_login AS login," +
							"       chatrooms_owner.f_password AS password," +
							"       chatrooms_owner.f_owner_id AS user_id," +
							"       chatrooms_owner.f_id AS o_id," +
							"       chatrooms_owner.f_name AS o_name," +
							"       chatrooms_signin.f_owner_id AS s_owner_id," +
							"       chatrooms_signin.f_id AS s_id," +
							"       chatrooms_signin.f_name AS s_name " +
							"FROM chatrooms_owner FULL OUTER JOIN chatrooms_signin" +
							"    ON chatrooms_owner.f_login = chatrooms_signin.f_login;");
			statement.setLong(1, (long) page * size + 1);
			statement.setLong(2, (long) page * size + size);

			ResultSet resultSet = statement.executeQuery();
			Map<Long, User> users = new HashMap<>();

			while (resultSet.next()) {
				long user_id = resultSet.getLong("user_id");
				User user;

				if (users.containsKey(user_id)) {
					user = users.get(user_id);
				} else {
					user = new User(
							user_id,
							resultSet.getString("login"),
							resultSet.getString("password")
					);
				}

				Chatroom owner_room = new Chatroom(
						resultSet.getLong("o_id"),
						resultSet.getString("o_name"),
						user);

				if (!user.getCreatedRooms().contains(owner_room)) {
					user.getCreatedRooms().add(owner_room);
				}

				Chatroom signin_room = new Chatroom(
						resultSet.getLong("s_id"),
						resultSet.getString("s_name"),
						null);

				if (!user.getSignedRooms().contains(signin_room)) {
					user.getSignedRooms().add(signin_room);
				}

				users.put(user_id, user);
			}

			return new ArrayList<>(users.values());
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

		return new ArrayList<>();
	}
}
