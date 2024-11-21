package com.hjhotelback.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailDTO {
	private Integer paymentId;
	private Integer reservationId;
	private String memberName;
	private String paymentMethod; 
	private String paymentStatus; 
	private BigDecimal amount;
	private String transactionId; 
	private LocalDateTime paymentDate;
	private LocalDateTime paymentStatusUpdate;
	private List<PaymentProcessLog> paymentProcessLogs;
	
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PaymentProcessLog {
        private LocalDateTime created_at;
        private String status;
	}
	
}
