package edu.school21.sockets.utils;

import java.io.Serializable;

public class JsonDto implements Serializable {
	private final String message;
	private final Long fromId;
	private final Long roomId;

	public JsonDto(String message, Long fromId, Long roomId) {
		this.message = message;
		this.fromId = fromId;
		this.roomId = roomId;
	}

	public String getMessage() {
		return message;
	}

	public Long getFromId() {
		return fromId;
	}

	public Long getRoomId() {
		return roomId;
	}

	@Override
	public String toString() {
		return "{\n" +
				"\t\"message\": \"" + message + "\",\n" +
				"\t\"fromId\": \"" + fromId + "\",\n" +
				"\t\"roomId\": \"" + roomId + "\"\n" +
				"}";
	}
}
