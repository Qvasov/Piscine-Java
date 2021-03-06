package school21.spring.service.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import school21.spring.service.models.User;

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
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = (:id);", map, new BeanPropertyRowMapper<>(User.class)));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
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
		jdbcTemplate.update("INSERT INTO users (id, email) VALUES (:id, :email);", map);
	}

	@Override
	public void update(User entity) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", entity.getId())
				.addValue("email", entity.getEmail());
		jdbcTemplate.update("UPDATE users SET email = (:email) WHERE id = (:id);", map);
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		jdbcTemplate.update("DELETE FROM users WHERE id = (:id);", map);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("email", email);
		return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = (:email)", map, new BeanPropertyRowMapper<>(User.class)));
	}
}
