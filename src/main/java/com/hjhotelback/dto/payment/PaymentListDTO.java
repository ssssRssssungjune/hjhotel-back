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
public class PaymentListDTO {
	private Integer paymentId;
	private Integer reservationId;
	private BigDecimal amount;
	private String paymentMethod;
	private String status;
	private String transactionId;
	private Integer memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 생성 시점에 자동으로 시간 설정
    public PaymentListDTO(Integer reservationId, BigDecimal amount, String paymentMethod, String status, String transactionId) {
        this.reservationId = reservationId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.transactionId = transactionId;
        this.createdAt = LocalDateTime.now();  // 현재 시간
        this.updatedAt = LocalDateTime.now();  // 현재 시간
    }
}
