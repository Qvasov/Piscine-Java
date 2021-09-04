package edu.school21.sockets.app;

import java.io.*;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		if (args.length == 1 && args[0].matches("--port=\\d{0,5}")) {
			try (Socket socket = new Socket("localhost", Integer.parseInt(args[0].substring(7)));
			     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			     PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
				while (true) {
					String inputCmd = in.readLine();

					if (inputCmd == null || inputCmd.equals("null")) {
						break;
					}

					System.out.println(inputCmd);

					if (inputCmd.equals("Successful!")) {
						break;
					} else {
						out.println(reader.readLine());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
