package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

public class MessagesRepositoryImpl implements MessagesRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public MessagesRepositoryImpl() {
	}

	@Override
	public Optional<Message> findById(long id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM messages WHERE id = (:id);",
					map, new BeanPropertyRowMapper<>(Message.class)));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Message> findAll() {
		return jdbcTemplate.query("SELECT * FROM messages ORDER BY id;", new BeanPropertyRowMapper<>(Message.class));
	}

	@Override
	public void save(Message entity) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("author_id", entity.getAuthorId())
				.addValue("room_id", entity.getRoomId())
				.addValue("text", entity.getText());
		jdbcTemplate.update("INSERT INTO messages (author_id, room_id, text) VALUES (:author_id, :room_id, :text);", map);
	}

	@Override
	public void update(Message entity) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", entity.getId())
				.addValue("author_id", entity.getAuthorId())
				.addValue("room_id", entity.getRoomId())
				.addValue("text", entity.getText())
				.addValue("datetime", entity.getDatetime());
		jdbcTemplate.update("UPDATE messages SET author_id = (:author_id), room_id = (:room_id)," +
				" text = (:text), datetime = (:datetime) WHERE id = (:id);", map);
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		jdbcTemplate.update("DELETE FROM messages WHERE id = (:id);", map);
	}

	@Override
	public List<Message> findLastMessages(Long limit) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("limit", limit);
		return jdbcTemplate
				.query("WITH t AS (SELECT * FROM messages ORDER BY datetime DESC LIMIT (:limit)) SELECT * FROM t ORDER BY datetime;",
				map,
				new BeanPropertyRowMapper<>(Message.class)
		);
	}
}
