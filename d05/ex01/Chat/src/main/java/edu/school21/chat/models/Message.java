package edu.school21.chat.models;

import java.sql.Timestamp;
import java.util.Date;

public class Message {
	private Long id;
	private User author;
	private Chatroom room;
	private String text;
	private Date datetime;

	public Message(Long id, User author, Chatroom room, String text, Date datetime) {
		this.id = id;
		this.author = author;
		this.room = room;
		this.text = text;
		this.datetime = datetime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Message message = (Message) o;

		if (!id.equals(message.id)) return false;
		if (!author.equals(message.author)) return false;
		if (!room.equals(message.room)) return false;
		if (!text.equals(message.text)) return false;
		return datetime.equals(message.datetime);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + author.hashCode();
		result = 31 * result + room.hashCode();
		result = 31 * result + text.hashCode();
		result = 31 * result + datetime.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id=" + id +
				", author=" + author +
				", room=" + room +
				", text='" + text + '\'' +
				", date=" + datetime +
				'}';
	}
}
