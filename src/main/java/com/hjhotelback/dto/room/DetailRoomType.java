package com.hjhotelback.dto.room;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetailRoomType {
	private String roomNumber;
	private Integer floor;
	private RoomStatus status;
	private String view;	
	private String roomTypeName;
}
