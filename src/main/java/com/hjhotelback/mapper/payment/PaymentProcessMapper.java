package com.hjhotelback.mapper.payment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hjhotelback.dto.payment.PaymentProcessDTO;
import com.hjhotelback.dto.payment.PaymentProcessListDTO;

@Mapper
public interface PaymentProcessMapper {

	// 24.11.23 지은 [완료] : 전체 결제 프로세스 내역 조회
	List<PaymentProcessListDTO> getAllPaymentProcesses();
	
	// 24.11.23 지은 [완료] : 결제 프로세스 등록
	void insertPaymentProcess(PaymentProcessDTO paymentProcessDTO);
}
