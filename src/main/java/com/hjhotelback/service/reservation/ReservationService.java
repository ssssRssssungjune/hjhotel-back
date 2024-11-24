package com.hjhotelback.service.reservation;

import java.util.List;
import java.sql.Timestamp;

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
    //24.11.21 한택 [예정 작업] : 유효성 검사 필요 why 안정성을 위해서 / 추가 의문 - 안정성 검사를 서비스 계층까지 와서 작업해야되나
    public void createReservation(ReqReservation.Create req){
        // 24.11.24 한택 [작업 정리] : 유효성 검사에 대한 정리 내용 
        // 사용자 고유 id , 객실 고유 id , 예약하려는 기간(checkin , checkout) , 총금액(객실 1인 숙박비용 * 기간)
        // 프론트에서도 유효성검사를 하지만 안정성을 위해 검사하야될 것 
        //  1. 사용자가 유효한 사용자인지 - db에서 검사
        //  2.  객실이 있고 해당 기간에 사용이 가능한지 - db에서 검사
        //  3.  checin < checkout - 비즈니스 로직에서 처리(백엔드 처리)
        
        if(req.checkIn.compareTo(req.checkOut) <= 0)
            _mapper.createReservation(req.memberId, req.roomId, req.checkIn, req.checkOut, req.totalAmount);
        
    }

    //24.11.21 한택 [보류] : 사용자와 관리자 조회를 따로 만들지 생각해봐야 함 / 현재는 기능 동일
    public ResReservation.Detail getReservationDetail(int reservationId){
        return _mapper.getReservationById(reservationId);
    }

    public List<ResReservation.Detail> getReservationList(){
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

