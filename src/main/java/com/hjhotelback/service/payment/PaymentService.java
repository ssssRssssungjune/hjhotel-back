package com.hjhotelback.service.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjhotelback.dto.payment.PaymentCreateDTO;
import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.mapper.payment.PaymentMapper;

@Service
public class PaymentService {

	@Autowired
	private PaymentMapper paymentMapper;
	
	// 결제 내역 목록 조회
	public List<PaymentDTO> getPaymentsList() {
		return paymentMapper.getPaymentsList();
	};
	
	// 결제 등록
	public void createPayment(PaymentCreateDTO paymentCreateDTO) {
		paymentMapper.createPayment(paymentCreateDTO);
	}
}
