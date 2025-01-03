package com.hjhotelback.dto.room;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class CountTypeDto {
	private Integer roomTypeId;
	private String name;
	private String description;
	private Integer baseOccupancy;
	private Integer maxOccupancy;
	private BigDecimal basePrice;
	private Integer roomCount;
	private String imagePath;
}
