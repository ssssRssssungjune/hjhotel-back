package com.hjhotelback.dto.room;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TypeDetailDto_Client {
	private String roomTypeName;
	private String roomTypeDescription;
	private Integer baseOccupancy;
	private Integer maxOccupancy;
	private BigDecimal basePrice;
	private boolean isActive;
	private String imagePath;
	private String amenityName;
}
