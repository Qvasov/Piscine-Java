package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.RoomsRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsersServiceImpl implements UsersService {

	@Autowired
	@Qualifier("usersRepository")
	UsersRepository usersRepository;

	@Autowired
	@Qualifier("roomsRepository")
	RoomsRepository roomsRepository;

	@Autowired
	@Qualifier("messagesRepository")
	MessagesRepository messagesRepository;

	@Autowired
	@Qualifier("bCryptPasswordEncoder")
	BCryptPasswordEncoder encoder;

	@Override
	public boolean signUp(String username, String password) {
		if (usersRepository.findByUsername(username).isPresent()) {
			return false;
		} else {
			User newUser = new User(username, encoder.encode(password));
			usersRepository.save(newUser);
			return true;
		}
	}

	@Override
	public Optional<User> signIn(String username, String password) {
		Optional<User> user = usersRepository.findByUsername(username);
		return user.filter(value -> encoder.matches(password, value.getPassword()));
	}

	@Override
	public boolean createRoom(String username, String roomName) {
		if (!roomsRepository.findByRoomName(roomName).isPresent()) {
			Optional<User> user = usersRepository.findByUsername(username);
			if (user.isPresent()) {
				Room newRoom = new Room(roomName, user.get().getId());
				roomsRepository.save(newRoom);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Room> getRooms() {
		return roomsRepository.findAll();
	}

	@Override
	public Optional<Room> getRoom(String number) {
		if (number.matches("\\d+")) {
			int i = Integer.parseInt(number) - 1;
			if (i >= 0) {
				List<Room> rooms = roomsRepository.findAll();
				if (rooms.size() == i) {
					return Optional.of(new Room("Exit", 0L));
				}
				return roomsRepository.findById(rooms.get(i).getId());
			}
		}
		return Optional.empty();
	}

	@Override
	public void saveMessage(Long authorId, Long roomId, String text) {
		messagesRepository.save(new Message(authorId, roomId, text));
	}

	@Override
	public String getLastMessages() {
		return messagesRepository.findLastMessages(30L).stream()
				.map((o) -> new StringBuilder()
						.append(usersRepository.findById(o.getAuthorId())
								.orElseGet(() -> new User("unknown user", null))
								.getUsername())
						.append(": ")
						.append(o.getText()))
				.collect(Collectors.joining("\n"));
	}
}
