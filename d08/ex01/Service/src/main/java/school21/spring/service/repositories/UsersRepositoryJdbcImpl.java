package school21.spring.service.repositories;

import school21.spring.service.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

	private DataSource dataSource;

	public UsersRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Optional<User> findById(long id) {
		try (Connection connection = dataSource.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?;");
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return Optional.of(new User(resultSet.getLong("id"), resultSet.getString("email")));
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public List<User> findAll() {
		List<User> userList = new ArrayList<>();
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users;");
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				userList.add(new User(resultSet.getLong("id"), resultSet.getString("email")));
			}

			preparedStatement.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return userList;
	}

	@Override
	public void save(User entity) {
		try (Connection connection = dataSource.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (id, email) VALUES (?, ?);");
			preparedStatement.setLong(1, entity.getId());
			preparedStatement.setString(2, entity.getEmail());
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	@Override
	public void update(User entity) {
		try (Connection connection = dataSource.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET email = ? WHERE id = ?;");
			preparedStatement.setString(1, entity.getEmail());
			preparedStatement.setLong(2, entity.getId());
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		try (Connection connection = dataSource.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?;");
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	@Override
	public Optional<User> findByEmail(String email) {
		try (Connection connection = dataSource.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?;");
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return Optional.of(new User(resultSet.getLong("id"), resultSet.getString("email")));
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return Optional.empty();
	}
}
