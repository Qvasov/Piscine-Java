package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.utils.FormatReader;
import edu.school21.sockets.utils.FormatWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static edu.school21.sockets.server.UserConnectionState.*;

public class UserConnection extends Thread {
	private UsersService usersService;
	private Socket socket;
	private FormatReader in;
	private FormatWriter out;
	private Long userId;
	private String username;
	private Long roomId;

	private UserConnectionState state;

	public UserConnection(Socket socket) throws IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
		this.socket = socket;
		this.usersService = context.getBean(UsersService.class);
		this.out = new FormatWriter(new ObjectOutputStream(socket.getOutputStream()), this);
		this.in = new FormatReader(new ObjectInputStream(socket.getInputStream()));
		this.state = MAIN_MENU;
		start();
	}

	@Override
	public void run() {
		try {
			out.println("Hello from Server!");
			while (true) {
				if (this.isInterrupted()) {
					return;
				}

				out.println(state.getMsg());
				String input = in.readLine();
				switch (state) {
					case MAIN_MENU:
						mainMenu(input);
						break;
					case SIGN_IN_USERNAME:
					case SIGN_IN_PASSWORD:
						signIn(input);
						break;
					case SIGN_UP_USERNAME:
					case SIGN_UP_PASSWORD:
						signUp(input);
						break;
					case ROOM_MENU:
						roomMenu(input);
						break;
					case CREATE_ROOM:
						createRoom(input);
						break;
					case CHOOSE_ROOM:
						CHOOSE_ROOM.clearMsg();
						chooseRoom(input);
						break;
					case MESSAGING:
						MESSAGING.clearMsg();
						messaging(input);
						break;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			if (e instanceof SocketException) {
				Server.chatRooms.get(roomId).remove(this);
				Server.userConnections.remove(this);
			} else {
				e.printStackTrace();
			}
		}
	}

	private void mainMenu(String input) throws IOException {
		switch (input) {
			case "1":
				state = SIGN_IN_USERNAME;
				break;
			case "2":
				state = SIGN_UP_USERNAME;
				break;
			case "3":
				out.println("!ForceQuit");
				Server.userConnections.remove(this);
				this.interrupt();
				break;
		}
	}

	private void signIn(String input) throws IOException {
		switch (state) {
			case SIGN_IN_USERNAME:
				username = input;
				state = SIGN_IN_PASSWORD;
				break;
			case SIGN_IN_PASSWORD:
				Optional<User> user = usersService.signIn(username, input);
				if (user.isPresent()) {
					if (Server.isSignIn(user.get().getId())) {
						out.println("This user already sign in.");
					} else {
						Server.userConnections.add(this);
						userId = user.get().getId();
						username = user.get().getUsername();
						state = ROOM_MENU;
						break;
					}
				} else {
					out.println("Password is no correct.");
				}
				userId = null;
				username = null;
				state = MAIN_MENU;
				break;
		}
	}

	private void signUp(String input) throws IOException {
		switch (state) {
			case SIGN_UP_USERNAME:
				username = input;
				state = SIGN_UP_PASSWORD;
				break;
			case SIGN_UP_PASSWORD:
				if (usersService.signUp(username, input)) {
					out.println("Successful!");
				} else {
					out.println("This user already exist.");
				}
				username = null;
				state = MAIN_MENU;
				break;
		}
	}

	private void roomMenu(String input) {
		switch (input) {
			case "1":
				state = CREATE_ROOM;
				break;
			case "2":
				List<Room> rooms = usersService.getRooms();
				StringJoiner newMsg = new StringJoiner("\n\t");
				newMsg.add("Rooms:");

				for (int i = 0; i <= rooms.size(); i++) {
					if (i == rooms.size()) {
						newMsg.add((i + 1) + ". Exit");
					} else {
						newMsg.add((i + 1) + ". " + rooms.get(i).getRoomName());
					}
				}

				CHOOSE_ROOM.setMsg(newMsg.toString());
				state = CHOOSE_ROOM;
				break;
			case "3":
				Server.userConnections.remove(this);
				state = MAIN_MENU;
				break;
		}
	}

	private void createRoom(String input) throws IOException {
		if (usersService.createRoom(username, input)) {
			out.println("Successful!");
		} else {
			out.println("This room already exist.");
		}
		state = ROOM_MENU;
	}

	private void chooseRoom(String input) throws IOException {
		Optional<Room> room = usersService.getRoom(input);
		if (room.isPresent()) {
			roomId = room.get().getId();

			if (roomId == null && room.get().getRoomName().equalsIgnoreCase("Exit")) {
				state = ROOM_MENU;
				return;
			} else if (!Server.chatRooms.containsKey(roomId)) {
				Server.chatRooms.put(roomId, new LinkedList<>());
			}

			sendMsg(username + " has joined the chat.");
			out.println(usersService.getLastMessages());
			List<UserConnection> chatroom = Server.chatRooms.get(roomId);
			chatroom.add(this);
			MESSAGING.setMsg("Java Room " + room.get().getRoomName());
			state = MESSAGING;
		}
	}

	private void messaging(String input) throws IOException {
		if (input.equals("Exit")) {
			Server.chatRooms.get(roomId).remove(this);
			out.println("You have left the chat.");
			input = username + " has left the chat.";
			sendMsg(input);
			roomId = null;
			state = ROOM_MENU;
		} else {
			usersService.saveMessage(userId, roomId, input);
			sendMsg(username + ": " + input);
		}
	}

	private void sendMsg(String input) throws IOException {
		for (UserConnection uc : Server.chatRooms.get(roomId)) {
			uc.out.println(input);
		}
	}

	public Long getUserId() {
		return userId;
	}

	public Long getRoomId() {
		return roomId;
	}
}
