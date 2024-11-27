package com.hjhotelback.dto.room;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RoomDetail {
	private String roomTypeName;
	private String roomTypeDescription;
	private Integer baseOccupancy;
	private Integer maxOccupancy; 
	private BigDecimal basePrice;
	private String imagePath;
	private String roomNumber;
	private Integer floor;
	private String view;
}
