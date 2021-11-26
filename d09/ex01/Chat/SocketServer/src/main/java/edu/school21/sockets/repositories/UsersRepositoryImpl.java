package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public UsersRepositoryImpl() {
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
		map.addValue("username", entity.getUsername())
				.addValue("password", entity.getPassword());
		jdbcTemplate.update("INSERT INTO users (username, password) VALUES (:username, :password);", map);
	}

	@Override
	public void update(User entity) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", entity.getId())
				.addValue("username", entity.getUsername());
		jdbcTemplate.update("UPDATE users SET username = (:username) WHERE id = (:id);", map);
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		jdbcTemplate.update("DELETE FROM users WHERE id = (:id);", map);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("username", username);
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = (:username)",
					map, new BeanPropertyRowMapper<>(User.class)));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
