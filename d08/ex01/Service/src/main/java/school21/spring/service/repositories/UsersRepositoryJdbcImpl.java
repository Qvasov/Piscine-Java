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

		return Optional.empty();
	}

	@Override
	public List<User> findAll() {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users;");
			ResultSet resultSet = preparedStatement.executeQuery();
			List<User> userList = new ArrayList<>();

			while (resultSet.next()) {
				userList.add(new User(resultSet.getLong("id"), resultSet.getString("email")));
			}

			preparedStatement.close();
			return userList;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(User entity) {

	}

	@Override
	public void update(User entity) {

	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public Optional<User> findByEmail(String email) {
		return Optional.empty();
	}
}
