package com.hjhotelback.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderListDTO {
    private Integer id;					// 주문 고유 ID
    private String paypalOrderId;		// PayPal에서 받은 주문 ID
    private String status;				// 주문 상태 (PENDING, COMPLETED, FAILED, CANCELLED)
    private Integer reservationId;		// 상품 ID (예: 예약된 객실 ID), reservationId(예약 ID)
    private BigDecimal amount;			// 결제 금액
    private LocalDateTime createdAt;	// 주문 생성 시간
}
