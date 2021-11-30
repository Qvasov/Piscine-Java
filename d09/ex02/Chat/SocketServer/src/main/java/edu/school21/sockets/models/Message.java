package edu.school21.sockets.models;

import java.sql.Timestamp;

public class Message {
	private Long id;
	private Long authorId;
	private Long roomId;
	private String text;
	private Timestamp datetime;

	public Message() {
	}

	public Message(Long authorId, Long roomId, String text) {
		this.authorId = authorId;
		this.roomId = roomId;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getDatetime() {
		return datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}
}
