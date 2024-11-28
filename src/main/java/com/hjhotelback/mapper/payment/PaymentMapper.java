package com.hjhotelback.mapper.payment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Update;

import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentDetailDTO;
import com.hjhotelback.dto.payment.PaymentListDTO;
import com.hjhotelback.dto.payment.PaymentReservationListDTO;

@Mapper
public interface PaymentMapper {
	
    // 결제 정보 저장
    void insertPayment(@Param("transactionId") String transactionId,
                       @Param("amount") double amount,
                       @Param("status") String status);
	
	// 24.11.22 지은 [완료] : 전체 결제 내역 목록 조회
	List<PaymentListDTO> getPaymentsList();
	
	// 24.11.21 지은 [완료] : 결제 내역 - 결제 내역 등록
	void createPayment(PaymentDTO paymentDTO);
	
	// 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 상세 조회
	@ResultMap("paymentDetailResultMap")
	PaymentDetailDTO getPaymentById(@Param("paymentId") Integer paymentId);
	
	// 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 삭제
	int deletePayment(@Param("paymentId") Integer paymentId);
	
	// 24.11.22 지은 [작업 중] : 결제 내역 - 특정 결제 내역 상태 변경
	void updatePaymentStatus(PaymentDTO newPaymentDTO);
	
	// 24.11.23 지은 [완료] : 결제 내역 - payment DB 속성 전체 가져오기
	PaymentDTO getPaymentByIdStatus(@Param("paymentId") Integer paymentId);
	
	// 예약내역결제조회 (결제 전)
	PaymentReservationListDTO getReservationPaymentList();
}
