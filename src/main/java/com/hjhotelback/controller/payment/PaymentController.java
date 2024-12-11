package com.hjhotelback.controller.payment;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.hjhotelback.mapper.payment.paypal.ProductMapper;
import com.hjhotelback.service.payment.PayPalService;
import com.hjhotelback.service.payment.PaymentService;
import com.hjhotelback.service.reservation.ReservationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users/payments")
@RequiredArgsConstructor
public class PaymentController {
	
	private final PaymentService paymentService;
	private final PaymentMapper paymentMapper;
	
    // 24.11.26 지은 [완료] : 예약 결제 내역 조회 (결제 전)
    @GetMapping("/reservationList/{reservationId}")
    public ResponseEntity<?> reservationPaymentList(
    		@PathVariable("reservationId") Integer reservationId) {
    	try {
    		PaymentReservationListDTO paymentReservationListDTO = paymentService.getReservationPaymentList(reservationId);
    		return ResponseEntity.ok(paymentReservationListDTO);
    	} catch (Exception e) {
    		// 예외 발생 시 로그 출력
	        log.error("Error during payment creation", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("예약 내역을 불러오는 도중 오류 발생");
    	}
    	
    }  
}
