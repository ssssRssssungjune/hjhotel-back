package com.hjhotelback.controller.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.payment.PaymentProcessListDTO;
import com.hjhotelback.mapper.payment.PaymentProcessMapper;
import com.hjhotelback.service.payment.PaymentProcessService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/payment-processes")
public class PaymentProcessController {
	
	@Autowired
	private PaymentProcessService paymentProcessService;
	
	@Autowired
	private PaymentProcessMapper paymentProcessMapper;

	// 24.11.23 지은 [완료] : 전체 결제 프로세스 내역 조회
	@GetMapping
	public List<PaymentProcessListDTO> getAllPaymentProcesses() {
		return paymentProcessService.getAllPaymentProcesses();
	}

	// 24.11.23 지은 [작업 예정] : 결제 프로세스 등록
	@PostMapping
	public String createPaymentProcess() {
		return "결제 프로세스 등록";
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
