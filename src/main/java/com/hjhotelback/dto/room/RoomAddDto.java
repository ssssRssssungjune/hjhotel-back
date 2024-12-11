package com.hjhotelback.dto.room;

import lombok.Data;

@Data
public class RoomAddDto {
	private Integer roomTypeId;
	private String roomNumber;
	private Integer floor;
	private RoomView view;
	private RoomStatus status;
}
