package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;

public class Main {

	public static void main(String[] args) {
		if (args.length == 1 && args[0].matches("--port=\\d{0,5}")) {
			new Server(Integer.parseInt(args[0].substring(7))).start();
		}
	}
}
