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
	private Integer paymentId;
	private Integer reservationId;
	private String paymentMethod;
	private PaymentStatus paymentStatus;
	private BigDecimal amount;
	private String transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
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
