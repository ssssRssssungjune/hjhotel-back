package com.hjhotelback.dto.payment;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentPageDTO {
	private int page;
	private int size;
	private int totalPages;
	private long totalElements;
	private List<PaymentListDTO> content;
}
