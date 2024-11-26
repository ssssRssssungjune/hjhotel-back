package com.hjhotelback.controller.payment;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hjhotelback.dto.payment.PaymentReservationListDTO;
import com.hjhotelback.dto.payment.PaymentStatus;
import com.hjhotelback.mapper.payment.PaymentMapper;
import com.hjhotelback.service.payment.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private PaymentMapper paymentMapper;

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
            @RequestParam(name = "paymentStatus") PaymentStatus paymentStatus,
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
    
    // 24.11.23 지은 [완료] : 결제 내역 - 특정 결제 내역 상태 변경
    @PutMapping("/{paymentId}/status")
    public ResponseEntity<Map<String, Object>> updatePaymentStatus(
    		@PathVariable("paymentId") Integer paymentId,
    		@RequestParam(name = "newStatus") PaymentStatus newStatus) {
    	
    	try {
	    	// 상태 업데이트를 위한 DTO 생성
	        PaymentDTO newPaymentDTO = new PaymentDTO();
	        newPaymentDTO.setPaymentId(paymentId);
	        newPaymentDTO.setPaymentStatus(newStatus);
	        newPaymentDTO.setUpdatedAt(LocalDateTime.now());
	        
	        boolean isUpdated = paymentService.updatePaymentStatus(newPaymentDTO);
	        
	        log.info("test", isUpdated);
	        
	        // 상태 업데이트가 성공한 경우
	        if (isUpdated) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("statusUpdated", true);
	            response.put("message", "Payment status updated successfully.");
	            return ResponseEntity.ok(response); // HTTP 200 OK
	        } else {
	            // 상태가 변경되지 않았거나 결제 정보가 없을 경우
	            Map<String, Object> response = new HashMap<>();
	            response.put("statusUpdated", false);
	            response.put("message", "Payment status not changed or payment not found.");
	            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(response); // HTTP 304 Not Modified
	        }
	        
    	} catch(Exception e) {
    		// 예외 발생 시
            log.error("Error updating payment status", e);

            Map<String, Object> response = new HashMap<>();
            response.put("statusUpdated", false);
            response.put("message", "Error updating payment status.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // HTTP 500 Internal Server Error
    	}
        
    }
    
    // 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 삭제
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable("paymentId") Integer paymentId) {
    	boolean isDeleted = paymentService.deletePayment(paymentId);
    	if (isDeleted) {
    		return ResponseEntity.noContent().build();
    	} else {
    		return ResponseEntity.notFound().build();
    	}
    }
    
    // 24.11.26 지은 [완료] : 예약 결제 내역 조회 (결제 전)
    @GetMapping("/reservation-list")
    public PaymentReservationListDTO reservationPaymentList() {
    	
    	return paymentService.getReservationPaymentList();
    }
}
