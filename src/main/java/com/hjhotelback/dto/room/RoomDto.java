package com.hjhotelback.dto.room;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomDto{
	private Integer reservationId;
	private Integer roomId;
	private String name;
	private String roomNumber;
	private Integer floor;
	private RoomStatus status;
	private String view;
}
