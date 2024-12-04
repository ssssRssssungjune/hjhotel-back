package com.hjhotelback.dto.room;

 public enum RoomStatus {
	
	    AVAILABLE("AVAILABLE"), 
	    OCCUPIED("OCCUPIED");
	 
	 	private String roomStatus;

		RoomStatus(String roomStatus) {
			// TODO Auto-generated constructor stub
			this.roomStatus = roomStatus;
			
		}
		public String getRoomStatus() {
	        return this.roomStatus;
	    }

	
	
}