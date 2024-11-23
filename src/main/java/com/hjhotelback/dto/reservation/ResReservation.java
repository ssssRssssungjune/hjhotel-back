package com.hjhotelback.dto.reservation;

import java.sql.Timestamp;

public class ResReservation {

    public static class List{
        public int reservationId;
        public String userName;
        public String roomType;
        public int baseOccupancy;
        public int maxOccupancy;
        public String roomNumber;
        public Timestamp checkIn;
        public Timestamp checkOut;
        public String status;
        public int totalAmount;
    }

    //24.11.21 한택 [변수이름]
    //sql colName / reservation_id, reservation_user_name, room_type, base_occupancy, max_occupancy, reservation_room, check_in, check_out, STATUS, 
    //              total_amount
    //java varName / reservationId, userName, roomType, baseOccupancy, maxOccupancy, roomNumber, checkIn, checkOut, status, totalAmount
    public static class Detail{
        public int reservationId;
        public String userName;
        public String roomType;
        public int baseOccupancy;
        public int maxOccupancy;
        public String roomNumber;
        public Timestamp checkIn;
        public Timestamp checkOut;
        public String status;
        public int totalAmount;
    }
    
}
