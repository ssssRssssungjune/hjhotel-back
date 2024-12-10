package com.hjhotelback.service.payment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hjhotelback.dto.payment.Order;
import com.hjhotelback.dto.payment.OrderListDTO;
import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentDetailDTO;
import com.hjhotelback.dto.payment.PaymentListDTO;
import com.hjhotelback.dto.payment.PaymentPageDTO;
import com.hjhotelback.dto.payment.PaymentReservationListDTO;
import com.hjhotelback.dto.payment.PaymentStatus;
import com.hjhotelback.mapper.payment.PaymentMapper;
import com.hjhotelback.mapper.payment.paypal.OrderMapper;
import com.paypal.api.payments.Payment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentMapper paymentMapper;
	private final OrderMapper orderMapper;
	
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
	
	// 24.12.08 지은 [완료] : 결제 내역 - 특정 결제 내역 삭제
	// order 내역도 같이 삭제되도록 수정.
	public boolean deletePayment(Integer paymentId) {
		PaymentDTO paymentDTO = paymentMapper.getPaymentById(paymentId);
	    if (paymentDTO == null) {
	        return false;  // 결제 정보가 존재하지 않으면 삭제할 수 없음
	    }
	    // 결제 삭제
	    int deletedCount = paymentMapper.deletePayment(paymentId); 
		// Paypal 주문 삭제
	    int deletedPaypalCount = paymentMapper.deletePaypalOrder(paymentDTO.getOrderId());
	    
	    // 트랜잭션 롤백을 위해, 둘 다 삭제되었는지 체크
	    if (deletedCount > 0 && deletedPaypalCount > 0) {
	        return true;
	    } else {
	        // 만약 둘 중 하나라도 삭제되지 않았다면 롤백 처리
	        throw new RuntimeException("Deletion failed for either payment or paypal order");
	    }
	}
	
	// 24.12.08 지은 [완료] : 결제 내역 - 특정 결제 내역 상태 변경
	// order 내역도 같이 상태 변경
	public boolean updatePaymentStatusBasic(Integer paymentId, PaymentStatus newStatus) {
		
		PaymentDTO newPaymentDTO = paymentMapper.getPaymentById(paymentId);
		if (newPaymentDTO == null) {
        	return false;
        }
		
		// 상태 업데이트를 위한 DTO 생성
        newPaymentDTO.setPaymentId(paymentId);
        newPaymentDTO.setPaymentStatus(newStatus);
        newPaymentDTO.setUpdatedAt(LocalDateTime.now());
        
        Order order = paymentMapper.getPaymentByPaypalId(newPaymentDTO.getOrderId());
        order.setId(newPaymentDTO.getOrderId());
        order.setStatus(newStatus.toString());
        
        // 결제 상태 업데이트 저장
        int updatePaymentStatusCount = paymentMapper.updatePaymentStatusBasic(newPaymentDTO);
        // paypal 주문서 상태 업데이트 저장
        int updatePaypalStatusCount = paymentMapper.updatePaymentPaypalStatus(order);
        
        System.out.println("updatePaymentStatusCount: " + updatePaymentStatusCount + "updatePaypalStatusCount: " + updatePaypalStatusCount);
        // 트랜잭션 롤백을 위해, 둘 다 삭제되었는지 체크
	    if (updatePaymentStatusCount > 0 && updatePaypalStatusCount > 0) {
	    	return true;
	    } else {
	        // 만약 둘 중 하나라도 삭제되지 않았다면 롤백 처리
	        throw new RuntimeException("Deletion failed for either payment or paypal order");
	    }  
	}

	// 24.11.26 지은 [완료] : 예약 결제 내역 조회 (결제 전)
	public PaymentReservationListDTO getReservationPaymentList(Integer reservationId) {
		return paymentMapper.getReservationPaymentList(reservationId);
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
