package com.hjhotelback.service.payment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentDetailDTO;
import com.hjhotelback.dto.payment.PaymentListDTO;
import com.hjhotelback.dto.payment.PaymentReservationListDTO;
import com.hjhotelback.mapper.payment.PaymentMapper;
import com.paypal.api.payments.Payment;

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
	public PaymentDetailDTO getPaymentCustomById(Integer paymentId) {
		return paymentMapper.getPaymentCustomById(paymentId);
	}
	
	// 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 삭제
	public boolean deletePayment(Integer paymentId) {
		int deletedCount = paymentMapper.deletePayment(paymentId);
		return deletedCount > 0;
	}
	
	// 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 상태 변경
	public boolean updatePaymentStatusBasic(PaymentDTO newPaymentDTO) {

        if (newPaymentDTO != null) {
            paymentMapper.updatePaymentStatusBasic(newPaymentDTO);
            return true;
        }

        // 결제 정보가 없거나 상태가 동일하면 false 반환
        return false;
	}

	
	// 24.11.26 지은 [완료] : 예약 결제 내역 조회 (결제 전)
	public PaymentReservationListDTO getReservationPaymentList() {
		return paymentMapper.getReservationPaymentList();
	};
}
