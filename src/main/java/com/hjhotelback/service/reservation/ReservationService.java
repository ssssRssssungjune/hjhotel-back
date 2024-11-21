package com.hjhotelback.service.reservation;

import org.springframework.stereotype.Service;

import com.hjhotelback.dto.reservation.ReqReservation;
import com.hjhotelback.mapper.reservation.ReservationMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    /*
        24.11.20 한택 [작업 일정]
        1주차
        GET/api/reservation	
        POST/api/reservation	
    */

    public enum Status{
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

    private final ReservationMapper _mapper;

    //24.11.21 한택 [변수이름]
    //sql colName / member_id, room_id, check_in, check_out, status, total_amount
    //java varName ReqReservation.Create.~ / memberId, roomId, checkIn, checkOut, totalAmount
    public void createReservation(ReqReservation.Create req){
        _mapper.createReservation(req.memberId, req.roomId, req.checkIn, req.checkOut, req.totalAmount);

    }

    public void getReservationDetail(int reservationId){
        _mapper.getReservationById(reservationId);
    }

    public void getReservationList(){

    }

    public void updateReservation(){

    }

    public void cancelReservation(){

    }




    // 분리 해야됨 체크인 , 체크아웃
    public void setRoomCheckInOut(){

    }
}

