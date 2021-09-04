package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private final int port;

	public Server(int port) {
		this.port = port;
	}

	public void start() {
		ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
		UsersService usersService = context.getBean(UsersService.class);

		try (ServerSocket serverSocket = new ServerSocket(port);
		     Socket socket = serverSocket.accept();
		     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		     PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true)
		     ) {
			out.println("Hello from Server!");

			while (true) {
				String inputCmd = in.readLine();
				System.out.println(inputCmd);

				if (inputCmd.equals("signUp")) {
					out.println("Enter username:");
					String username = in.readLine();
					out.println("Enter password:");
					String password = in.readLine();
					if (usersService.signUp(username, password)) {
						out.println("Successful!");
						break;
					} else {
						out.println("Enter command:");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
