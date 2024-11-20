package com.hjhotelback.mapper.payment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentRequestDTO;

@Mapper
public interface PaymentMapper {

	List<PaymentDTO> getPaymentsList();
	void createPayment(PaymentRequestDTO paymentRequestDTO);
}
