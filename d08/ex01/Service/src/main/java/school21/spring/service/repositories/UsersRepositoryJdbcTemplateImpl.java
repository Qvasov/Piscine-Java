package school21.spring.service.repositories;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import school21.spring.service.model.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;

	public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Optional<User> findById(long id) {
		return Optional.empty();
	}

	@Override
	public List<User> findAll() {
		jdbcTemplate.query("SELECT * FROM users;", new BeanPropertyRowMapper<>(User.class));
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
