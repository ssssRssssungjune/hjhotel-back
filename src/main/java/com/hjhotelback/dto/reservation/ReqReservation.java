package com.hjhotelback.dto.reservation;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class ReqReservation {
    
    //24.11.21 한택 [변수이름]
    //sql colName / member_id, room_id, check_in, check_out, total_amount
    //java varName / memberId, roomId, checkIn, checkOut, totalAmount
    public static class Create{
        public int memberId;
        public int roomId;
        public long checkIn;
        public long checkOut;
        public int totalAmount;

        public Timestamp getCheckIn() {
            return new Timestamp(checkIn);  // long 값을 Timestamp로 변환
        }
    
        public Timestamp getCheckOut() {
            return new Timestamp(checkOut); // long 값을 Timestamp로 변환
        }
    }

    public static class Delete{
        public int reservationId;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = UpdateDate.class, name = "UpdateDate"),
        @JsonSubTypes.Type(value = UpdateState.class, name = "UpdateState"),
        @JsonSubTypes.Type(value = UpdateRoom.class, name = "UpdateRoom")
    })
    public interface Update{ }

    public static class UpdateDate implements Update{
        public int reservationId;
        public Timestamp checkIn;
        public Timestamp checkOut;
    }

    public static class UpdateState implements Update{
        public int reservationId;
        public ReservationStatus status;
    }

    public static class UpdateRoom implements Update{
        public int reservationId;
        public int roomId;
    }

}
