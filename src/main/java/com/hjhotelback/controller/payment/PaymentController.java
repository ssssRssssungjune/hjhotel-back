package com.hjhotelback.controller.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentRequestDTO;
import com.hjhotelback.service.payment.PaymentService;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;

	// 결제 내역 목록 조회
	@GetMapping
	public List<PaymentDTO> getPayments() {
		return paymentService.getPaymentsList();
	}
	
	// 특정 결제 내역 조회
	@GetMapping("1") // {payment_id}
	public String getPaymentById() {
		return "getPaymentById: 특정 결제 내역 조회";
	}
	
    // 결제 등록
    @PostMapping
    public void createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
    	paymentService.createPayment(paymentRequestDTO);
    }
    
    // 결제 상태 변경
    @PutMapping("/1/status") // {payment_id}
    public String updatePaymentStatus() {
    	return "updatePaymentStatus: 결제 상태 변경";
    }
    
    // 결제 내역 삭제
    @DeleteMapping("/1") // {payment_id}
    public String deletePayment() {
    	return "deletePayment: 결제 내역 삭제";
    }

}
