package edu.school21.sockets.models;

public class Room {

	private Long id;
	private String roomName;
	private Long ownerId;

	public Room() {
	}

	public Room(String roomName, Long ownerId) {
		this.roomName = roomName;
		this.ownerId = ownerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
}
