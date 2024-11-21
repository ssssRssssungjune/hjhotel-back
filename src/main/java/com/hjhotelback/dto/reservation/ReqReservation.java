package com.hjhotelback.dto.reservation;

import java.sql.Timestamp;

public class ReqReservation {
    
    //24.11.21 한택 [변수이름]
    //sql colName / member_id, room_id, check_in, check_out, total_amount
    //java varName / memberId, roomId, checkIn, checkOut, totalAmount
    public class Create{
        public int memberId;
        public int roomId;
        public Timestamp checkIn;
        public Timestamp checkOut;
        public int totalAmount;
    }

    //0 : PENDING , 1 : CONFIRMED , 2 : CANCELLED , 3 : COMPLETED
    public class Modify{
        public int itype;
    }
}
