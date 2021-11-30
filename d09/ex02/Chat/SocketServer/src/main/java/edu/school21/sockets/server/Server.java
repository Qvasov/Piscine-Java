package edu.school21.sockets.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Server {
	private final int port;
	public static List<UserConnection> userConnections = new LinkedList<>();
	public static Map<Long, List<UserConnection>> chatRooms = new HashMap<>();

	public Server(int port) {
		this.port = port;
	}

	public void start() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				Socket socket = serverSocket.accept();
				try {
					new UserConnection(socket);
				} catch (IOException e) {
					System.err.println(e.getMessage());
					socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static boolean isSignIn(Long userId) {
		for (UserConnection uc : userConnections) {
			if (uc.getUserId().equals(userId)) {
				return true;
			}
		}
		return false;
	}
}
