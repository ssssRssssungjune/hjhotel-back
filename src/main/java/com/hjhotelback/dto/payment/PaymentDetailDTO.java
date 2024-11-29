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
	private Integer paymentId;				// 결제ID
	private Integer processId;				// 결제프로세스ID
	private Integer reservationId;			// 예약ID
	private String memberName;				// 회원이름
	private String paymentMethod; 			// 결제방식(ex-card, paypal 등등)
	private String paymentStatus; 			// 'PENDING','PROCESSING','COMPLETED','FAILED','CANCELLED','REFUNDED'
	private BigDecimal amount;				// 총 금액
	private String transactionId; 			// 트랜잭션ID
	private LocalDateTime paymentDate;		// 결제한 날
	private LocalDateTime paymentUpdate;	// 결제 수정 날
	private List<PaymentProcessLogDTO> paymentProcessLogs;	// 결제 프로세스 로그 목록

}
