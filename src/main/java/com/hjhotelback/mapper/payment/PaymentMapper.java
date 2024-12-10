package com.hjhotelback.mapper.payment;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Update;
import org.springframework.http.ResponseEntity;

import com.hjhotelback.dto.payment.Order;
import com.hjhotelback.dto.payment.OrderListDTO;
import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentDetailDTO;
import com.hjhotelback.dto.payment.PaymentListDTO;
import com.hjhotelback.dto.payment.PaymentReservationListDTO;

@Mapper
public interface PaymentMapper {
	
	// 24.12.06 지은 [완료] : 전체 결제 내역 목록 조회
	List<PaymentListDTO> getPaymentsList(@Param("size") Integer size, @Param("offset") Integer offset);
	
	// 24.12.06 지은 [완료] : 전체 결제 내역 목록 갯수
	Integer countTotal();
	
	// 24.12.06 지은 [완료] : payapl 주문서 내역 목록 갯수
	Integer countTotalPayapl();
	
	// 24.11.21 지은 [완료] : 결제 내역 - 결제 내역 등록
	void createPayment(PaymentDTO paymentDTO);
	
	// 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 상세 조회
	@ResultMap("paymentDetailResultMap")
	PaymentDetailDTO getPaymentCustomById(@Param("paymentId") Integer paymentId);
	
	// 24.11.22 지은 [완료] : 결제 내역 - 특정 결제 내역 삭제
	int deletePayment(@Param("paymentId") Integer paymentId);
	
	// 24.12.07 지은 [완료] : 주문서 내역 - 특저 주문 내역 삭제
	int deletePaypalOrder(Integer ordeId);
	
	// 24.12.08 지은 [완료] : 결제 내역 - 특정 결제 내역 상태 변경
	int updatePaymentStatusBasic(PaymentDTO newPaymentDTO);
	
    // 24.11.29 지은 [완료] : paypal 결제시 성공인 경우 결제 업데이트 내용 저장
    void updatePaymentStatus(PaymentDTO newPaymentDTO);
	
	// 24.11.23 지은 [완료] : 결제 내역 - paymentId로 payment DB 속성 전체 가져오기
	PaymentDTO getPaymentById(@Param("paymentId") Integer paymentId);
	
	// 24.12.04 지은 [완료] : 결제 내역 - orderId로 payment DB 속성 전체 가져오기
	PaymentDTO getPaymentByOrderId(@Param("orderId") Integer orderId);
	
	// 24.11.29 지은 [완료] : 예약내역결제조회 (결제 전)
	PaymentReservationListDTO getReservationPaymentList(Integer reservationId);
	
	// 24.12.05 지은 [완료] : paypal 주문 내역 전체 목록. pagination 기능 추가.
	List<OrderListDTO> getPaypalAllList(@Param("size") Integer size, @Param("offset") Integer offset);
	
	// 24.12.06 지은 [완료] : paypal 특정 주문서 내역 조회
	Order getPaymentByPaypalId(@Param("id") Integer id);
	
	// 24.12.08 지은 [완료] : paypal 특정 주문서 상태 업데이트 내용 저장
	int updatePaymentPaypalStatus(Order order);
}
