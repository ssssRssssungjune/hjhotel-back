package com.hjhotelback.dto.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoomView {

	CITY,
    STREET,
    DOWNTOWN;
	
	
//    private String view;
//    RoomView(String view) {
//    	this.view = view;
//    }
    		//직렬화
//    		@JsonValue
//    		public String getView() {
//    		return this.view;
//    		}
    	
    	 	//역직렬화
//    		@JsonCreator
//    		public static RoomView fromString(String value) {
//    		    // 1. @JsonValue로 지정된 값과 대소문자 구분 없이 비교
//    		    for (RoomView roomView : RoomView.values()) {
//    		        if (roomView.getView().equalsIgnoreCase(value)) {
//    		            return roomView;
//    		        }
//    		    }
//
//    		    // 2. enum 상수의 이름과 대소문자 구분 없이 비교 (City_View, CITY_VIEW 등 허용)
//    		    try {
//    		        return RoomView.valueOf(value.toUpperCase().replace(" ", "_"));
//    		    } catch (IllegalArgumentException e) {
//    		        // 
//    		    }
//
//    		    throw new IllegalArgumentException("Invalid view:" + value);
//    		}
    	 		


    	
    	
    }

    

