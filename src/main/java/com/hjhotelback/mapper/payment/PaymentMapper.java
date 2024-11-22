package com.hjhotelback.mapper.payment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentDetailDTO;
import com.hjhotelback.dto.payment.PaymentListDTO;

@Mapper
public interface PaymentMapper {
	
	// 24.11.22 지은 [완료] : 전체 결제 내역 목록 조회
	List<PaymentListDTO> getPaymentsList();
	
	// 24.11.21 지은 [완료] : 결제 내역 - 결제 내역 등록
	void createPayment(PaymentDTO paymentDTO);
	
	// 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 상세 조회
	@ResultMap("paymentDetailResultMap")
	PaymentDetailDTO getPaymentById(@Param("paymentId") Integer paymentId);
	
	// 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 삭제
	int deletePayment(@Param("paymentId") Integer paymentId);
}
