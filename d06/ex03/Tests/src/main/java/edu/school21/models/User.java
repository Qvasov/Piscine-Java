package edu.school21.models;

public class User {
	long id;
	String login;
	String password;
	boolean Auth;

	public User(String login, String password, boolean auth) {
		this.login = login;
		this.password = password;
		Auth = auth;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAuth() {
		return Auth;
	}

	public void setAuth(boolean auth) {
		Auth = auth;
	}
}
