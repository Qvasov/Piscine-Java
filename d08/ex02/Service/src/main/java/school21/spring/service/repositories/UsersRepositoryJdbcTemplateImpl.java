package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import java.util.List;
import java.util.Optional;

@Component("UsersRepositoryJdbcTemplateImpl")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public UsersRepositoryJdbcTemplateImpl() {
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
		map.addValue("email", entity.getEmail())
				.addValue("password", entity.getPassword());
		jdbcTemplate.update("INSERT INTO users (email, password) VALUES (:email, :password);", map);
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
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = (:email)", map, new BeanPropertyRowMapper<>(User.class)));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
