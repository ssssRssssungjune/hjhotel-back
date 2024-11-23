package com.hjhotelback.service.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjhotelback.dto.payment.PaymentListDTO;
import com.hjhotelback.dto.payment.PaymentProcessListDTO;
import com.hjhotelback.mapper.payment.PaymentProcessMapper;

@Service
public class PaymentProcessService {

	@Autowired
	private PaymentProcessMapper paymentProcessMapper;
	
	// 24.11.23 지은 [완료] : 전체 결제 프로세스 내역 조회
	public List<PaymentProcessListDTO> getAllPaymentProcesses() {
		return paymentProcessMapper.getAllPaymentProcesses();
	}
	
}