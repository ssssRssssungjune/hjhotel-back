package com.hjhotelback.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailDTO {
	private Integer paymentId;
	private Integer processId;
	private Integer reservationId;
	private String memberName;
	private String paymentMethod; 
	private String paymentStatus; 
	private BigDecimal amount;
	private String transactionId; 
	private LocalDateTime paymentDate;
	private LocalDateTime paymentUpdate;
	private List<PaymentProcessLogDTO> paymentProcessLogs;

}
