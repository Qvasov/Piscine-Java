package school21.spring.service.repositories;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import school21.spring.service.model.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private RowMapper<User> USER_ROW_MAPPER = (resultSet, i) ->
			new User(resultSet.getLong("id"), resultSet.getString("email"));

	public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Optional<User> findById(long id) {
		return Optional.empty();
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM users;", new BeanPropertyRowMapper<>(User.class));
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
