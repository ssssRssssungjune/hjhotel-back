package com.hjhotelback.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
	private Integer paymentId;				// 결제ID
	private Integer reservationId;			// 예약ID
	private String paymentMethod;			// 결제방식(ex-card, paypal 등등)
	private PaymentStatus paymentStatus;	// 'PENDING','PROCESSING','COMPLETED','FAILED','CANCELLED','REFUNDED'
	private BigDecimal amount;				// 총 금액
	private String transactionId;			// 트랜잭션ID
    private LocalDateTime createdAt;		// 결제한 날
    private LocalDateTime updatedAt;		// 결제 수정 날
    
    // 생성 시점에 자동으로 시간 설정
    public PaymentDTO(Integer reservationId, String paymentMethod, PaymentStatus paymentStatus, BigDecimal amount, String transactionId) {
        this.reservationId = reservationId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.createdAt = LocalDateTime.now();  // 현재 시간
        this.updatedAt = LocalDateTime.now();  // 현재 시간
    }
}
