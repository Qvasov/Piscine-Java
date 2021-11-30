package edu.school21.sockets.services;

import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {

	boolean signUp(String username, String password);

	Optional<User> signIn(String username, String password);

	boolean createRoom(String username, String roomName);

	List<Room> getRooms();

	Optional<Room> getRoom(String number);

	void saveMessage(Long authorId, Long roomId, String text);

	String getLastMessages();
}
