package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
	private Long id;
	private String login;
	private String password;
	private List<Chatroom> createdRooms;
	private List<Chatroom> signedRooms;

	public User(Long id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.createdRooms = new ArrayList<>();
		this.signedRooms = new ArrayList<>();
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id.equals(user.id) &&
				login.equals(user.login)  &&
				password.equals(user.password) &&
				createdRooms.equals(user.createdRooms) &&
				signedRooms.equals(user.signedRooms);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, createdRooms, signedRooms);
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

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public List<Chatroom> getCreatedRooms() {
		return createdRooms;
	}

	public List<Chatroom> getSignedRooms() {
		return signedRooms;
	}
}
