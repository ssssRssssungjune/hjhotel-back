package com.hjhotelback.dto.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoomView {
    CITY_VIEW("City View"),
    STREET_VIEW("Street View"),
    DOWNTOWN_VIEW("Downtown View");

    private String view;
    RoomView(String view) {
    	this.view = view;
    }
    		//직렬화
    		@JsonValue
    		public String getView() {
    		return this.view;
    		}
    	
    	 	//역직렬화
    		@JsonCreator
    		public static RoomView fromString(String value) {
    		    try {
    		        return RoomView.valueOf(value.toUpperCase());
    		    } catch(IllegalArgumentException e) {
    		        throw new IllegalArgumentException("Invalid view:" + value);
    		    }
    		}

    	 		


    	
    	
    }

    

