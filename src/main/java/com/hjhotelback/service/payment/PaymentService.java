package com.hjhotelback.service.payment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjhotelback.dto.payment.Order;
import com.hjhotelback.dto.payment.OrderListDTO;
import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentDetailDTO;
import com.hjhotelback.dto.payment.PaymentListDTO;
import com.hjhotelback.dto.payment.PaymentPageDTO;
import com.hjhotelback.dto.payment.PaymentReservationListDTO;
import com.hjhotelback.mapper.payment.PaymentMapper;
import com.paypal.api.payments.Payment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentMapper paymentMapper;
	
	// 24.12.06 지은 [완료] : 전체 결제 내역 목록 조회(page, size)
	public PaymentPageDTO getPaymentsList(int page, int size) {
		int offset = (page - 1) * size;
		List<PaymentListDTO> content = paymentMapper.getPaymentsList(size, offset);
		int totalElements = paymentMapper.countTotal();
		int totalPages = (int) Math.ceil((double) totalElements / size);

		PaymentPageDTO paymentPageDTO = new PaymentPageDTO(page, size, totalPages, totalElements, content);
		
		return paymentPageDTO;
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
	
	// 24.12.05 지은 [완료] : paypal 주문 내역 전체 목록. pagination 기능 추가.
	public PaymentPageDTO getPaypalAllList(int page, int size) {
		int offset = (page - 1) * size;
		List<OrderListDTO> content = paymentMapper.getPaypalAllList(size, offset);
		int totalElements = paymentMapper.countTotalPayapl();
		int totalPages = (int) Math.ceil((double) totalElements / size);
		
		PaymentPageDTO paymentPageDTO = new PaymentPageDTO(page, size, totalPages, totalElements, content);
		return paymentPageDTO;
	}
	
	// 24.12.06 지은 [완료] : paypal 특정 주문서 내역 조회
	public Order getPaymentByPaypalId(Integer id) {
		return paymentMapper.getPaymentByPaypalId(id);
	}
}
