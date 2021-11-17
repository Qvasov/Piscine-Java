package edu.school21.exceptions;

public class AlreadyAuthenticatedException extends RuntimeException {

	public AlreadyAuthenticatedException() {
		super();
	}

	public AlreadyAuthenticatedException(String message) {
		super(message);
	}

	public AlreadyAuthenticatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyAuthenticatedException(Throwable cause) {
		super(cause);
	}
}
