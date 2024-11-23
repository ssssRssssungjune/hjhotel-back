package com.hjhotelback.controller.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.payment.PaymentProcessDTO;
import com.hjhotelback.dto.payment.PaymentProcessListDTO;
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
	
	// 24.11.23 지은 [작업 예정] : 특정 결제 프로세스 상태 수정
	@PutMapping("1/status") //{process_id}
	public String updatePaymentProcessStatus() {
		return "특정 결제 프로세스 상태 수정";
	}
	
	// 24.11.23 지은 [작업 예정] : 특정 결제 프로세스 삭제
	@DeleteMapping("1")//{process_id}
	public String deletePaymentProcess() {
		return "특정 결제 프로세스 삭제";
	}

}
