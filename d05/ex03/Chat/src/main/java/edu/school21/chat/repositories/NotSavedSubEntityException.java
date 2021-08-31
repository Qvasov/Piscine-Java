package edu.school21.chat.repositories;

public class NotSavedSubEntityException extends RuntimeException{

	public NotSavedSubEntityException() {
		super();
	}

	public NotSavedSubEntityException(String message) {
		super(message);
	}

	public NotSavedSubEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotSavedSubEntityException(Throwable cause) {
		super(cause);
	}
}
