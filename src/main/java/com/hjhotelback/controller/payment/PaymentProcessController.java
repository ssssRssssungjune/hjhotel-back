package com.hjhotelback.controller.payment;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/payment-processes")
public class PaymentProcessController {

	// 전체 결제 프로세스 내역 조회
	@GetMapping
	public String getAllPaymentProcesses() {
		return "전체 결제 프로세스 내역 조회";
	}

	// 결제 프로세스 등록
	@PostMapping
	public String createPaymentProcess() {
		return "결제 프로세스 등록";
	}
	
	// 특정 결제 프로세스 상태 수정
	@PutMapping("1/status") //{process_id}
	public String updatePaymentProcessStatus() {
		return "특정 결제 프로세스 상태 수정";
	}
	
	// 특정 결제 프로세스 삭제
	@DeleteMapping("1")//{process_id}
	public String deletePaymentProcess() {
		return "특정 결제 프로세스 삭제";
	}

}
