package com.hjhotelback.mapper.reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hjhotelback.dto.reservation.ReqReservation;
import com.hjhotelback.dto.reservation.ResReservation;


@Mapper
public interface ReservationMapper {

    //관리자 예약 목록 조회
    public List<ResReservation.Detail> getReservationList(Map<String,Object> param);

    //관리자 예약 목록 마지막 예약Id
    public int getLastReservationId();
    
    //사용자/관리자 예약 상세 조회
    public ResReservation.Detail getReservationById(@Param("reservationId") int reservationId);
    
    //사용자 예약 생성
    public int createReservation(ReqReservation.Create req);
    
    // 관리자/사용자 예약 날짜 변경
    public int updateReservationDate(ReqReservation.UpdateDate req);

    //관리자 예약 객실 변경
    public int updateReservationRoom(ReqReservation.UpdateRoom req);
    
    //관리자 예약 상태 변경 'COMPLETED' 일 경우 변경 불가 
    public int updateReservationState(ReqReservation.UpdateState req);
    
    //사용자 예약 취소
    public int deleteReservation(ReqReservation.Delete req);

    // Test
    public List<ResReservation.Summary> getRoomReservationSummary(LocalDate date);

    public List<ResReservation.RoomSample> getRoomSample();
}
