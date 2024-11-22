package com.hjhotelback.controller.payment;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentDetailDTO;
import com.hjhotelback.dto.payment.PaymentListDTO;
import com.hjhotelback.service.payment.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;

	// 24.11.22 지은 [완료] : 전체 결제 내역 목록 조회
	@GetMapping
	public List<PaymentListDTO> getPayments() {
		return paymentService.getPaymentsList();
	}
	
	// 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 상세 조회
	@GetMapping("{paymentId}/details")
	public PaymentDetailDTO getPaymentById(@PathVariable("paymentId") Integer paymentId) {
		return paymentService.getPaymentById(paymentId);
	}
	
    // 24.11.21 지은 [완료] : 결제 내역 - 결제 내역 등록
	@PostMapping
	public ResponseEntity<?> createPayment(
			@RequestParam(name = "reservationId") Integer reservationId,
            @RequestParam(name = "amount") BigDecimal amount,
            @RequestParam(name = "paymentMethod") String paymentMethod,
            @RequestParam(name = "paymentStatus") String paymentStatus,
            @RequestParam(name = "transactionId") String transactionId) {
		try {
	        // 결제 정보 생성
	        PaymentDTO paymentDTO = new PaymentDTO();
	        paymentDTO.setReservationId(reservationId);
	        paymentDTO.setAmount(amount);
	        paymentDTO.setPaymentMethod(paymentMethod);
	        paymentDTO.setPaymentStatus(paymentStatus);
	        paymentDTO.setTransactionId(transactionId);
	        
	        // 생성 시점에 자동으로 날짜 설정
	        paymentDTO.setCreatedAt(LocalDateTime.now());
	        paymentDTO.setUpdatedAt(LocalDateTime.now());

	        // DB에 결제 정보 저장
	        paymentService.createPayment(paymentDTO);

	        return ResponseEntity.ok("Payment initiated");

	    } catch (Exception e) {
	        // 예외 발생 시 로그 출력
	        log.error("Error during payment creation", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error during payment creation");
	    }
    }
    
    // 24.11.22 지은 [예정 작업] : 결제 내역 - 특정 결제 내역 상태 변경
    @PutMapping("/1/status") // {payment_id}
    public String updatePaymentStatus() {
    	return "updatePaymentStatus: 결제 상태 변경";
    }
    
    // 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 삭제
    @DeleteMapping("/{paymentId}") // {payment_id}
    public ResponseEntity<Void> deletePayment(@PathVariable("paymentId") Integer paymentId) {
    	boolean isDeleted = paymentService.deletePayment(paymentId);
    	if (isDeleted) {
    		return ResponseEntity.noContent().build();
    	} else {
    		return ResponseEntity.notFound().build();
    	}
    }
}
