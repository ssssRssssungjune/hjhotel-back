package com.hjhotelback.dto.room;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;
@Data
public class RoomTypeDto_Client {
	private Integer roomTypeId;
	private String name;
	private String description;
	private Integer baseOccupancy;
	private Integer maxOccupancy;
	private BigDecimal basePrice;
	private String imagePath;
	
}