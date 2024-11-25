package com.hjhotelback.dto.room;
import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomDto{
	private Integer roomId;
	private Integer roomTypeId;
	private String roomNumber;
	private Integer floor;
	private Date createdAt;
	private RoomStatus status;
	private String view;	
	
	public RoomDto(Integer roomTypeId, String roomNumber, Integer floor, String view) {
	    this.roomTypeId = roomTypeId;
	    this.roomNumber = roomNumber;
	    this.floor = floor;
	    this.view = view;
	}
}
