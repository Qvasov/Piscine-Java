package edu.school21.sockets.utils;

import java.io.IOException;
import java.io.ObjectInputStream;

public class FormatReader {
	private final ObjectInputStream in;

	public FormatReader(ObjectInputStream in) {
		this.in = in;
	}

	public String readLine() throws IOException, ClassNotFoundException {
		JsonDto json = (JsonDto) in.readObject();
		System.out.println(json);
		return json.getMessage();
	}

	private boolean isJson(String string) {
		return string.matches("^\\{(\\s*\"\\w\":\\s*\"\\w\",)*\\s*\"\\w\":\\s*\"\\w\"\\s*}$");
	}
}
