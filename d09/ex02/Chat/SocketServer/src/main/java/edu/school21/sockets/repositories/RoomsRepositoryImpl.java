package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

public class RoomsRepositoryImpl implements RoomsRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public RoomsRepositoryImpl() {
	}

	@Override
	public Optional<Room> findById(long id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM rooms WHERE id = (:id);",
					map, new BeanPropertyRowMapper<>(Room.class)));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Room> findAll() {
		return jdbcTemplate.query("SELECT * FROM rooms ORDER BY id;", new BeanPropertyRowMapper<>(Room.class));
	}

	@Override
	public void save(Room entity) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("room_name", entity.getRoomName())
				.addValue("owner_id", entity.getOwnerId());
		jdbcTemplate.update("INSERT INTO rooms (room_name, owner_id) VALUES (:room_name, :owner_id);", map);
	}

	@Override
	public void update(Room entity) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", entity.getId())
				.addValue("room_name", entity.getRoomName())
				.addValue("owner_id", entity.getOwnerId());
		jdbcTemplate.update("UPDATE rooms SET room_name = (:room_name), owner_id = (:owner_id) WHERE id = (:id);", map);
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		jdbcTemplate.update("DELETE FROM rooms WHERE id = (:id);", map);
	}

	@Override
	public Optional<Room> findByRoomName(String roomName) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("room_name", roomName);
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM rooms WHERE room_name = (:room_name)",
					map, new BeanPropertyRowMapper<>(Room.class)));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
