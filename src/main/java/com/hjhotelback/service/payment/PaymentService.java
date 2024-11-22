package com.hjhotelback.service.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentDetailDTO;
import com.hjhotelback.dto.payment.PaymentListDTO;
import com.hjhotelback.mapper.payment.PaymentMapper;

@Service
public class PaymentService {

	@Autowired
	private PaymentMapper paymentMapper;
	
	// 결제 내역 목록 조회
	public List<PaymentListDTO> getPaymentsList() {
		return paymentMapper.getPaymentsList();
	};
	
	// 결제 등록
	public void createPayment(PaymentDTO paymentDTO) {
		paymentMapper.createPayment(paymentDTO);
	}
	
	// 특정 결제 상세 조회
	public PaymentDetailDTO getPaymentById(Integer paymentId) {
		return paymentMapper.getPaymentById(paymentId);
	}
}
