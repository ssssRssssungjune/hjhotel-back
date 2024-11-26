package com.hjhotelback.dto.room;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomAmenityDto {
	private String roomTypeName;
	private String amenityName;
	private String amenitydescription;
	private boolean amenityActive;
}