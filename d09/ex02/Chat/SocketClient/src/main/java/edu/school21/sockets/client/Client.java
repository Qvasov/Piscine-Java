package edu.school21.sockets.client;

import edu.school21.sockets.utils.JsonDto;

import java.io.*;
import java.net.Socket;

public class Client {
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private BufferedReader reader;
	private Thread writeListener;
	private Thread readListener;
	private Long userId;
	private Long roomId;

	public Client(int port) {
		try {
			socket = new Socket("localhost", port);
			try {
				this.in = new ObjectInputStream(socket.getInputStream());
				this.out = new ObjectOutputStream(socket.getOutputStream());
				this.reader = new BufferedReader(new InputStreamReader(System.in));
				writeListener = new WriteMsg();
				readListener = new ReadMsg();
				writeListener.start();
				readListener.start();
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
					input = readLine();
					if (input.equalsIgnoreCase("!ForceQuit")) {
						writeListener.interrupt();
						socket.close();
						System.exit(0);
					}
					if (!input.isEmpty()) {
						System.out.println(input);
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private String readLine() throws IOException, ClassNotFoundException {
		JsonDto json = (JsonDto) in.readObject();
		userId = json.getFromId();
		roomId = json.getRoomId();
		return json.getMessage();
	}

	public class WriteMsg extends Thread {
		@Override
		public void run() {
			while (true) {
				String input;
				try {
					input = reader.readLine();
					write(input);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void write(String message) throws IOException {
		JsonDto jsonDto = new JsonDto(message, userId, roomId);
		out.writeObject(jsonDto);
		out.flush();
	}
}
