package com.hjhotelback.dto.room;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomAmenityDto {
	private Integer amenityId;
	private Integer roomTypeId;
	private String name;
	private String description;
	private boolean isActive;
}