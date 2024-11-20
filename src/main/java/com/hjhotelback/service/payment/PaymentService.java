package com.hjhotelback.service.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.mapper.payment.PaymentMapper;

@Service
public class PaymentService {

	@Autowired
	private PaymentMapper paymentMapper;
	
	public List<PaymentDTO> getPaymentsList() {
		return paymentMapper.getPaymentsList();
	};
}
