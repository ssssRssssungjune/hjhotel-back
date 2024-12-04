package com.hjhotelback.controller.payment;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.payment.PaymentProcessDTO;
import com.hjhotelback.dto.payment.PaymentProcessListDTO;
import com.hjhotelback.dto.payment.PaymentProcessStatus;
import com.hjhotelback.dto.payment.PaymentStatus;
import com.hjhotelback.service.payment.PaymentProcessService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/payment-processes")
public class PaymentProcessController {
	
	@Autowired
	private PaymentProcessService paymentProcessService;
	

	// 24.11.23 지은 [완료] : 전체 결제 프로세스 내역 조회
	@GetMapping
	public List<PaymentProcessListDTO> getAllPaymentProcesses() {
		return paymentProcessService.getAllPaymentProcesses();
	}

	// 24.11.23 지은 [완료] : 결제 프로세스 등록
	@PostMapping
	public ResponseEntity<?> createPaymentProcess(@RequestBody PaymentProcessDTO paymentProcessDTO) {
		try {
			paymentProcessService.createPaymentProcess(paymentProcessDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("success data" + paymentProcessDTO);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}	
	}
	
	// 24.11.24 지은 [완료] : 특정 결제 프로세스 상태 수정
	@PutMapping("{processId}/status")
	public ResponseEntity<Map<String, Object>> updatePaymentProcessStatus(
			@PathVariable("processId") Integer processId,
    		@RequestParam(name = "newStatus") PaymentProcessStatus newStatus) {
		try {
			// 상태 업데이트를 위한 객체 생성
			PaymentProcessDTO newPaymentProcessDTO = new PaymentProcessDTO();
			newPaymentProcessDTO.setProcessId(processId);
			newPaymentProcessDTO.setProcessStatus(newStatus);
			newPaymentProcessDTO.setCreatedAt(LocalDateTime.now());
			
			boolean isUpdated = paymentProcessService.updatePaymentProcessStatus(newPaymentProcessDTO);
			
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
	
	// 24.11.23 지은 [완료] : 특정 결제 프로세스 삭제
	@DeleteMapping("{processId}")
	public ResponseEntity<?> deletePaymentProcess(@PathVariable("processId") Integer processId) {
		try {
			paymentProcessService.deletePaymentProcess(processId);
			return ResponseEntity.status(HttpStatus.OK).body("success delete data" + processId);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());		
		}
	}

}
