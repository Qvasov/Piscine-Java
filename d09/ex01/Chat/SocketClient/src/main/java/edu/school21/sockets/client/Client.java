package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;

public class Client {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private BufferedReader reader;

	public Client(int port) {
		try {
			socket = new Socket("localhost", port);
			try {
				this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				this.reader = new BufferedReader(new InputStreamReader(System.in));
				new WriteMsg().start();
				new ReadMsg().start();
			} catch (IOException e) {
				socket.close();
				throw e;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private class ReadMsg extends Thread {
		@Override
		public void run() {
			String input;
			try {
				while (true) {
					input = in.readLine();
					if (input.equals("Exit")) {
						socket.close();
						break;
					}
					System.out.println(input);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public class WriteMsg extends Thread {
		@Override
		public void run() {
			while (true) {
				String input;
				try {
					input = reader.readLine();
					if (input.equals("Exit")) {
						out.println("Exit");
						break;
					}
					out.println(input);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
