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
	private Integer memberId;
	private String memberName;
	private String paymentMethod;
	private PaymentStatus paymentStatus;
	private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    
    // 생성 시점에 자동으로 시간 설정
    public PaymentListDTO(
        Integer paymentId,
        Integer reservationId, 
        Integer memberId,
        String memberName,
        String paymentMethod, 
        PaymentStatus paymentStatus,
        BigDecimal amount, 
        String transactionId) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
