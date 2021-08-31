package edu.school21.chat.models;

import java.util.List;

public class Chatroom {
	private Long id;
	private String name;
	private User owner;
	private List<Message> messages;

	public Chatroom(Long id, String name, User owner) {
		this.id = id;
		this.name = name;
		this.owner = owner;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Chatroom chatroom = (Chatroom) o;

		if (!id.equals(chatroom.id)) return false;
		if (!name.equals(chatroom.name)) return false;
		if (!owner.equals(chatroom.owner)) return false;
		return messages != null ? messages.equals(chatroom.messages) : chatroom.messages == null;
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + owner.hashCode();
		result = 31 * result + (messages != null ? messages.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Chatroom{" +
				"id=" + id +
				", name='" + name + '\'' +
				", owner=" + owner +
				", messages=" + messages +
				'}';
	}

	public Long getId() {
		return id;
	}
}
