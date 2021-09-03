package school21.spring.service.repositories;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import school21.spring.service.model.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private final RowMapper<User> USER_ROW_MAPPER = (resultSet, i) ->
			new User(resultSet.getLong("id"), resultSet.getString("email"));

	public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Optional<User> findById(long id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = (:id);", map, new BeanPropertyRowMapper<>(User.class)));
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM users;", new BeanPropertyRowMapper<>(User.class));
	}

	@Override
	public void save(User entity) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", entity.getId())
				.addValue("email", entity.getEmail());
		jdbcTemplate.query("INSERT INTO users (id, email) VALUES (:id, :email);", map, new BeanPropertyRowMapper<>(User.class));
	}

	@Override
	public void update(User entity) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", entity.getId())
				.addValue("email", entity.getEmail());
		jdbcTemplate.query("UPDATE users SET email = (:email) WHERE id = (:id);", map, new BeanPropertyRowMapper<>(User.class));
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		jdbcTemplate.query("DELETE FROM users WHERE id = (:id);", map, new BeanPropertyRowMapper<>(User.class));
	}

	@Override
	public Optional<User> findByEmail(String email) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("email", email);
		return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = (:id)", map, new BeanPropertyRowMapper<>(User.class)));
	}
}
