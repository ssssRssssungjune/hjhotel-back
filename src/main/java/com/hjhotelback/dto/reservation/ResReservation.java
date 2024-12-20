package com.hjhotelback.dto.reservation;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class ResReservation {

    @Setter
    @Getter
    public static class GetList {
        private int lastReservationId;
        private List<Detail> reservationList;
    }
    
    //24.11.21 한택 [변수이름]
    //sql colName / reservation_id, reservation_user_name, room_type, base_occupancy, max_occupancy, reservation_room, check_in, check_out, STATUS, 
    //              total_amount
    //java varName / reservationId, userName, roomType, baseOccupancy, maxOccupancy, roomNumber, checkIn, checkOut, status, totalAmount
    public static class Detail {
        public int reservationId;
        public String memberName;
        public String roomType;
        public int baseOccupancy;
        public int maxOccupancy;
        public String roomNumber;
        public Timestamp checkIn;
        public Timestamp checkOut;
        public String status;
        public int totalAmount;

    }
    
    @Setter
    @Getter
    public static class Create{
        private String message;
        private long reservationId;
    }
    
    public static class Update{
        public String message;
    }

    public static class Delete{
        public String message;
    }
    
    public static class Summary{
        public int roomTypeId;
        public Timestamp reservationDate;
        public int bookedRooms;
    }

    public static class RoomSample{
        public int roomTypeId;
        public String roomType;
        public String description;
        public int baseOccupancy;
        public int maxOccupancy;
        public int basePrice;
        public int roomCount;
    }
}
