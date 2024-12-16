package com.hjhotelback.dto.room;

import lombok.Data;
@Data
public class RoomTypeDto_Client {
	private Integer roomTypeId;
	private String name;
	private Integer baseOccupancy;
	private Integer maxOccupancy;
	private String imagePath;
	
	
	
}