package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{

    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            resultSet = statement.executeQuery(String.format("SELECT * FROM t_messages WHERE f_id = '%d';", id));

            if (!resultSet.next()) {
                return Optional.empty();
            }

            long authorId = resultSet.getLong("f_author_id");
            long chatroomId = resultSet.getLong("f_chatroom_id");
            String text = resultSet.getString("f_text");
            Timestamp datetime = resultSet.getTimestamp("f_datetime");
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
            connection.close();

            User user = new User(authorId, login, password);
            Chatroom chatroom = new Chatroom(chatroomId, roomName, user);
            Message message = new Message(user, chatroom, text, datetime);

            return Optional.of(message);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void save(Message message) {
        try (Connection connection = dataSource.getConnection()){
            if (message.getAuthor().getId() == null) {
                throw new NotSavedSubEntityException("User ID is null");
            }

            if (message.getRoom().getId() == null) {
                throw new NotSavedSubEntityException("Chatroom ID is null");
            }

            PreparedStatement statement;
            ResultSet resultSet;

            statement = connection.prepareStatement("SELECT * FROM t_users WHERE f_id = ?;");
            statement.setLong(1, message.getAuthor().getId());
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new NotSavedSubEntityException(
                        String.format("User with %d ID does not exist", message.getAuthor().getId()));
            }

            statement.close();
            resultSet.close();

            statement = connection.prepareStatement("SELECT * FROM t_chatrooms WHERE f_id = ?;");
            statement.setLong(1, message.getRoom().getId());
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new NotSavedSubEntityException(
                        String.format("Chatroom with %d ID does not exist", message.getRoom().getId()));
            }

            statement.close();
            resultSet.close();

            statement = connection.prepareStatement(
                    "INSERT INTO t_messages (f_author_id, f_chatroom_id, f_text, f_datetime) VALUES (?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, message.getAuthor().getId());
            statement.setLong(2, message.getRoom().getId());
            statement.setString(3, message.getText());
            statement.setTimestamp(4, message.getDateTime());
            statement.execute();
            resultSet = statement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new NotSavedSubEntityException("Error getting message ID");
            }

            message.setId(resultSet.getLong("f_id"));
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
