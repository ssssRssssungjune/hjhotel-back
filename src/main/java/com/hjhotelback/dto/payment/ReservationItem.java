package com.hjhotelback.dto.payment;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReservationItem {
    private int reservationId;         // 예약 고유 ID
    private String name;               // 회원 이름
    private String email;              // 회원 이메일 주소
    private String phone;              // 회원 연락처
    private String roomTypeName;       // 객실 타입
    private String roomNumber;         // 객실 호수 번호
    private Integer floor;             // 해당 객실 층 정보
    private Timestamp checkIn;         // 체크인 날짜 및 시간
    private Timestamp checkOut;        // 체크아웃 날짜 및 시간
    private BigDecimal totalAmount;	   // 총 가격
}
