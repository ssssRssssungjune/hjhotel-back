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
public class PaymentReservationListDTO {
	private Integer reservationId;
	private String roomName;
	private LocalDateTime checkIn;
	private LocalDateTime checkOut;
	private Integer baseOccupancy;
	private Integer maxOccupancy;
	private BigDecimal totalAmount;
}
