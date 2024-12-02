package com.hjhotelback.dto.payment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProcessListDTO {
	private Integer paymentId;
	private Integer processId;
	private Integer reservationId;
	private String paymentMethod;
	private String provider;
	private String sessionId;
	private PaymentProcessStatus processStatus;
	private String transactionId;
	private String redirectUrl;
	private LocalDateTime createdAt;
}
