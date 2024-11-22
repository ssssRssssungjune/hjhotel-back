package com.hjhotelback.mapper.payment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentDetailDTO;
import com.hjhotelback.dto.payment.PaymentListDTO;

@Mapper
public interface PaymentMapper {

	List<PaymentListDTO> getPaymentsList();
	void createPayment(PaymentDTO paymentDTO);
	@ResultMap("paymentDetailResultMap")
	PaymentDetailDTO getPaymentById(@Param("paymentId") Integer paymentId);
}
