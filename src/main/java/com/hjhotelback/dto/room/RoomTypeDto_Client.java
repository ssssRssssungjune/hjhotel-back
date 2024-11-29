package com.hjhotelback.dto.room;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;
@Data
public class RoomTypeDto_Client {
	private Integer roomTypeId;
	private String name;
	private Integer baseOccupancy;
	private Integer maxOccupancy;
	private String imagePath;
	
}