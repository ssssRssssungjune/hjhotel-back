package com.hjhotelback.dto.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoomStatus {
	
	AVAILABLE("AVAILABLE"),
	OCCUPIED("OCCUPIED");
	
	private String status;
	RoomStatus(String status){
		this.status = status;
	}
		//직렬화
		@JsonValue
		public String getStatus() {
		return this.status;
		}
	
		@JsonCreator
		public static RoomStatus fromString(String value) {
		    try {
		        return RoomStatus.valueOf(value.toUpperCase());
		    } catch(IllegalArgumentException e) {
		        throw new IllegalArgumentException("Invalid status:" + value);
		    }
		}


	
	
}