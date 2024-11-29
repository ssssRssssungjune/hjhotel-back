package com.hjhotelback.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Order {

    private Integer id; // 주문 고유 ID
    private String paypalOrderId; // PayPal에서 받은 주문 ID
    private String status; // 주문 상태 (PENDING, COMPLETED, FAILED, CANCELLED)
    private Integer reservationId; // 상품 ID (예: 예약된 객실 ID), reservationId(예약 ID)
    private BigDecimal amount; // 결제 금액
    private LocalDateTime createdAt; // 주문 생성 시간

    // 추가: ReservationItem 정보를 포함할 수 있습니다.
    private ReservationItem reservationItem; // 예약 정보 (예약과 연결된 DTO)
    
//    private Long id;
//    private String paypalOrderId;
//    private String status;
//    private Integer productId;
//    private BigDecimal amount;
//    private LocalDateTime createdAt;
//    private ReservationItem ReservationItem;
}
