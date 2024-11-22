package com.hjhotelback.mapper.payment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentDetailDTO;
import com.hjhotelback.dto.payment.PaymentListDTO;

@Mapper
public interface PaymentMapper {

	List<PaymentListDTO> getPaymentsList();
	void createPayment(PaymentDTO paymentDTO);
	PaymentDetailDTO getPaymentById(Integer paymentId);
}
