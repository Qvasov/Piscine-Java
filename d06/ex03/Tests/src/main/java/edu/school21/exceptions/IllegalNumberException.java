package edu.school21.exceptions;

public class IllegalNumberException extends RuntimeException {

	public IllegalNumberException() {
		super();
	}

	public IllegalNumberException(String message) {
		super(message);
	}

	public IllegalNumberException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalNumberException(Throwable cause) {
		super(cause);
	}
}
