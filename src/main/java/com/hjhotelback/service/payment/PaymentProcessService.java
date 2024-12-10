package com.hjhotelback.service.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjhotelback.dto.payment.PaymentProcessDTO;
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
	
	// 24.11.23 지은 [완료] : 결제 프로세스 등록
	public void createPaymentProcess(PaymentProcessDTO paymentProcessDTO) {
		paymentProcessMapper.insertPaymentProcess(paymentProcessDTO);
	}
	
	// 24.11.23 지은 [완료] : 특정 결제 프로세스 삭제
	public void deletePaymentProcess(Integer processId) {
		paymentProcessMapper.deletePaymentProcess(processId);
	}
	
	// 24.11.24 지은 [완료] : 특정 결제 프로세스 상태 수정
	public boolean updatePaymentProcessStatus(PaymentProcessDTO newPaymentProcessDTO) {
		if (newPaymentProcessDTO != null) {
			paymentProcessMapper.updatePaymentProcessStatus(newPaymentProcessDTO);
			return true;
		}
		
        // 결제 정보가 없거나 상태가 동일하면 false 반환
        return false;
	}
	
}
