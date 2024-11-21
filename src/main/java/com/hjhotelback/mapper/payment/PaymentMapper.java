package com.hjhotelback.mapper.payment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hjhotelback.dto.payment.PaymentCreateDTO;
import com.hjhotelback.dto.payment.PaymentDTO;

@Mapper
public interface PaymentMapper {

	List<PaymentDTO> getPaymentsList();
	void createPayment(PaymentCreateDTO paymentCreateDTO);
}
