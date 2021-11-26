package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.Socket;

public class UserConnection extends Thread {
	private UsersService usersService;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String username;

	public UserConnection(Socket socket) throws IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
		this.socket = socket;
		this.usersService = context.getBean(UsersService.class);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		start();
	}

	@Override
	public void run() {
		boolean signIn = false;

		try {
			out.println("Hello from Server!");

			while (true) {
				String input = in.readLine();

				if (input.equals("Exit")) {
					out.println("Exit");
					Server.userConnections.remove(this);
					if (signIn) {
						sendMsg(username + " has left the chat.");
					}
					break;
				}

				if (!signIn) {
					System.out.println(input);

					if (input.equals("signUp")) {
						signUp();
					} else if (input.equals("signIn")) {
						signIn = signIn();
						if (signIn) {
							Server.userConnections.add(this);
							sendMsg(username + " has joined the chat");
						}
					}
				} else {
					sendMsg(username + ": " + input);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMsg(String msg) {
		for (UserConnection ss : Server.userConnections) {
			ss.out.println(msg);
		}
	}

	private void signUp() throws IOException {
		out.println("Enter username:");
		String username = in.readLine();
		out.println("Enter password:");
		String password = in.readLine();

		if (usersService.signUp(username, password)) {
			out.println("Successful!");
		} else {
			out.println("This user already exist");
		}
	}

	private boolean signIn() throws IOException {
		out.println("Enter username:");
		this.username = in.readLine();
		out.println("Enter password:");
		String password = in.readLine();

		if (usersService.signIn(username, password)) {
			out.println("Start messaging");
			return true;
		} else {
			out.println("Password is no correct");
			return false;
		}
	}
}
