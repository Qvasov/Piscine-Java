package edu.school21.sockets.utils;

import edu.school21.sockets.server.UserConnection;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class FormatWriter {
	private final ObjectOutputStream out;
	private final UserConnection userConnection;


	public FormatWriter(ObjectOutputStream out, UserConnection userConnection) {
		this.out = out;
		this.userConnection = userConnection;
	}

	public void println(String message) throws IOException {
		JsonDto jsonDto = new JsonDto(message, userConnection.getUserId(), userConnection.getRoomId());
		System.out.println(jsonDto);
		out.writeObject(jsonDto);
		out.flush();
	}
}
