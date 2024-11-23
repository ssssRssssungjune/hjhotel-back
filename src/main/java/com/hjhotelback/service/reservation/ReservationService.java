package com.hjhotelback.service.reservation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hjhotelback.dto.reservation.ReqReservation;
import com.hjhotelback.dto.reservation.ResReservation;
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

    private final ReservationMapper _mapper;

    //24.11.21 한택 [변수이름]
    //sql colName / member_id, room_id, check_in, check_out, status, total_amount
    //java varName ReqReservation.Create.~ / memberId, roomId, checkIn, checkOut, totalAmount
    //24-11.21 한택 [예정 작업] : 유효성 검사 필요 why 안정성을 위해서 / 추가 의문 - 안정성 검사를 서비스 계층까지 와서 작업해야되나
    public void createReservation(ReqReservation.Create req){
        _mapper.createReservation(req.memberId, req.roomId, req.checkIn, req.checkOut, req.totalAmount);

    }

    //24.11.21 한택 [보류] : 사용자와 관리자 조회를 따로 만들지 생각해봐야 함 / 현재는 기능 동일
    public ResReservation.Detail getReservationDetail(int reservationId){
        return _mapper.getReservationById(reservationId);
    }

    public List<ResReservation.List> getReservationList(){
        return _mapper.getReservationList();
    }

    public void updateReservation(){

    }

    public void cancelReservation(){

    }




    // 분리 해야됨 체크인 , 체크아웃
    public void setRoomCheckInOut(){

    }
}

