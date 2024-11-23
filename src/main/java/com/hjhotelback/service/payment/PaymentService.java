package com.hjhotelback.service.payment;

import java.time.LocalDateTime;
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
	
	// 24.11.22 지은 [완료] : 전체 결제 내역 목록 조회
	public List<PaymentListDTO> getPaymentsList() {
		return paymentMapper.getPaymentsList();
	};
	
	// 24.11.21 지은 [완료] : 결제 내역 - 결제 내역 등록
	public void createPayment(PaymentDTO paymentDTO) {
		paymentMapper.createPayment(paymentDTO);
	}
	
	// 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 상세 조회
	public PaymentDetailDTO getPaymentById(Integer paymentId) {
		return paymentMapper.getPaymentById(paymentId);
	}
	
	// 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 삭제
	public boolean deletePayment(Integer paymentId) {
		int deletedCount = paymentMapper.deletePayment(paymentId);
		return deletedCount > 0;
	}
	
	// 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 상태 변경
	public boolean updatePaymentStatus(PaymentDTO newPaymentDTO) {

        if (newPaymentDTO != null) {
            paymentMapper.updatePaymentStatus(newPaymentDTO);
            return true;
        }

        // 결제 정보가 없거나 상태가 동일하면 false 반환
        return false;
	}

}
