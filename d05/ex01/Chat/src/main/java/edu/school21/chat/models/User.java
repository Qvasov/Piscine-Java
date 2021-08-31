package edu.school21.chat.models;

import java.util.List;

public class User {
	private Long id;
	private String login;
	private String password;
	private List<Chatroom> createdRooms;
	private List<Chatroom> signedRooms;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (!id.equals(user.id)) return false;
		if (!login.equals(user.login)) return false;
		if (!password.equals(user.password)) return false;
		if (createdRooms != null ? !createdRooms.equals(user.createdRooms) : user.createdRooms != null) return false;
		return signedRooms != null ? signedRooms.equals(user.signedRooms) : user.signedRooms == null;
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + login.hashCode();
		result = 31 * result + password.hashCode();
		result = 31 * result + (createdRooms != null ? createdRooms.hashCode() : 0);
		result = 31 * result + (signedRooms != null ? signedRooms.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", createdRooms=" + createdRooms +
				", signedRooms=" + signedRooms +
				'}';
	}
}
