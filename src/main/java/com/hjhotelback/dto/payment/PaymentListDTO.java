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
	private Integer orderId;
	private Integer reservationId;
	private String userId;
	private String memberName;
	private String paymentMethod;
	private PaymentStatus paymentStatus;
	private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
