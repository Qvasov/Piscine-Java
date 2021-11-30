package edu.school21.sockets.server;

public enum UserConnectionState {
	MAIN_MENU("\t1. SignIn\n" +
			"\t2. SignUp\n" +
			"\t3. Exit"),
	SIGN_IN_USERNAME("Enter username:"),
	SIGN_IN_PASSWORD("Enter password:"),
	SIGN_UP_USERNAME("Enter username:"),
	SIGN_UP_PASSWORD("Enter password:"),
	ROOM_MENU("\t1. Create room\n" +
			"\t2. Choose room\n" +
			"\t3. Exit"),
	CREATE_ROOM("Enter room name:"),
	CHOOSE_ROOM(""),
	MESSAGING("");

	private String msg;

	UserConnectionState(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void clearMsg() {
		this.msg = "";
	}
}
