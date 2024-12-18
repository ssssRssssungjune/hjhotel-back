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
	private List<?> content; // payment, order 둘다 사용해야해서 <?>으로 작성했음.
}
