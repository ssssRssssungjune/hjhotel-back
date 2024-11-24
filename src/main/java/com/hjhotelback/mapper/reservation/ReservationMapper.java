package com.hjhotelback.mapper.reservation;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hjhotelback.dto.reservation.ResReservation;


@Mapper
public interface ReservationMapper {

    //관리자 예약 목록 조회
    public List<ResReservation.Detail> getReservationList();
    
    //사용자/관리자 예약 상세 조회
    public ResReservation.Detail getReservationById(int reservationId);
    
    //사용자 예약 생성
    public void createReservation(int memberId, int roomId, Timestamp checkIn , Timestamp checkOut, int totalAmount);
    
    //사용자 예약 날짜 변경
    public void changeReservationDateForUser(int reservationId, int memberId, Timestamp checkIn, Timestamp checkOut);
    
    //관리자 예약 날짜 변경
    public void changeReservationDateForAdmin(int reservationId, Timestamp checkIn, Timestamp checkOut);
    
    //관리자 예약 객실 변경
    public void changeReservationRoomForAdmin(int reservationId, int roomId);
    
    //관리자 예약 상태 변경 'COMPLETED' 일 경우 변경 불가 
    public void changeReservationStatus(int ReservationId, String status);
    
    //사용자 예약 취소
    public void deleteReservationById(int ReservationId);
}
