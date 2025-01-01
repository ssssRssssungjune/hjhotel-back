package com.hjhotelback.controller.payment;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.payment.Order;
import com.hjhotelback.dto.payment.PaymentDTO;
import com.hjhotelback.dto.payment.PaymentStatus;
import com.hjhotelback.dto.payment.ReservationItem;
import com.hjhotelback.dto.reservation.ReqReservation;
import com.hjhotelback.dto.reservation.ReservationStatus;
import com.hjhotelback.mapper.payment.PaymentMapper;
import com.hjhotelback.mapper.payment.paypal.OrderMapper;
import com.hjhotelback.mapper.payment.paypal.ProductMapper;
import com.hjhotelback.service.payment.PayPalService;
import com.hjhotelback.service.reservation.ReservationService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users/paypal")
@RequiredArgsConstructor
public class PayPalController {
    private final PayPalService payPalService;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;
    private final ReservationService reservationService;
    
    // 24.11.29 지은 [완료] : order(주문서), payment(결제내역) 생성 완료.
    @GetMapping("/checkout/{reservationId}")
    public String checkout(@PathVariable("reservationId") Integer reservationId,
    		Authentication authentication) {
    	
    	// 인증된 사용자 이름 가져오기
        String username = authentication.getName();
        System.out.println("username: " + username);
    	
    	try {
    	
    		 ReservationItem reservationItem = productMapper.findById(reservationId);
    		 System.out.println("user id: " + reservationItem.getUserId());

     		 // 예약 내역이 없다면 결제 진행 안됨.
			 if (reservationItem == null) {
				 throw new RuntimeException("Reservation not found");
			 }
			 // 예약 내역의 상태가 PENDING이 아니면 결제 진행 안됨.
			 if (reservationItem.getStatus() != ReservationStatus.PENDING) {
				 throw new RuntimeException("The reservation has already been paid for. status: " + reservationItem.getStatus());
			 }
			 // 해당 사용자가 예약내역 사용자와 아이디가 동일한지 검사.
			 if (!username.equals(reservationItem.getUserId())) {
				 throw new RuntimeException("예약 내역의 사용자와 로그인한 사용자의 정보가 일치하지 않습니다.");
			 }

             // 주문서 생성
             Order order = new Order();
             order.setReservationId(reservationId);
             order.setAmount(reservationItem.getTotalAmount());
             order.setStatus("PENDING");
             order.setCreatedAt(LocalDateTime.now());
             Payment payment = payPalService.createPayment(
             		reservationItem.getTotalAmount(),
                     "USD",
                     "Payment for " + reservationItem.getName(),
                     "http://localhost:8080/api/users/paypal/cancel",
                     "http://localhost:8080/api/users/paypal/success"
             );
             order.setPaypalOrderId(payment.getId());
             orderMapper.insert(order);
             
             // 결제 내역 생성
             PaymentDTO paymentDTO = new PaymentDTO();
             paymentDTO.setOrderId(order.getId());
             paymentDTO.setReservationId(reservationId);
             paymentDTO.setPaymentMethod("PAYPAL");
             paymentDTO.setPaymentStatus(PaymentStatus.PENDING);
             paymentDTO.setAmount(order.getAmount());
             paymentDTO.setTransactionId(null);
             paymentMapper.createPayment(paymentDTO);

             return "redirect:" + payment.getLinks()
                     .stream()
                     .filter(link -> link.getRel().equals("approval_url"))
                     .findFirst()
                     .orElseThrow()
                     .getHref();
             
         } catch (PayPalRESTException e) {
        	 // PayPal에서 반환된 오류 메시지를 로그로 출력
        	 System.err.println("Error during PayPal payment creation: " + e.getMessage());
             // 에러 처리
             return "error" + e.getMessage(); //사용자에게 오류 메시지를 반환
         }
    	
    }
    
    // 24.12.04 지은 [완료] : 결제 오류 발생 부분 수정.
    // 쿼리문과 payment와 order 테이블을 연결하는 외부키 생성해서 공유.
    @Transactional
    @GetMapping("/success")
    public ResponseEntity<?> success(
    		@RequestParam(name="paymentId") String paymentId, 
    		@RequestParam(name="PayerID") String payerID,
    		@RequestParam(name = "token") String token) {
    	
    	// 각 값들을 가져오는지 테스트
    	System.out.println("Payment ID: " + paymentId);
    	System.out.println("Payer ID: " + payerID);
    	System.out.println("Token: " + token);
        
    	try {
        	// 결제 실행
            Payment payment = payPalService.executePayment(paymentId, payerID);

            // transactionId 추출
            String transactionId = payment.getTransactions().get(0).getRelatedResources().get(0)
                .getSale().getId();
            System.out.println("Transaction ID: " + transactionId);
            System.out.println("PaypalOrderId: " + payment.getId());
            
            // paypalOrderId 가져오기
            String paypalOrderId = payment.getId();
            Order order = orderMapper.findByPaypalOrderId(paypalOrderId);
            // orderId 가져오기
            Integer orderId = order.getId();
            
            // 결제 완료된 후 주문 상태 업데이트
            orderMapper.updateOrderStatus(paypalOrderId, "COMPLETED");            
            
            // orderId로 특정 결제내역 가져와서6 paymentId 가져오기.
            PaymentDTO newPaymentDTO = paymentMapper.getPaymentByOrderId(orderId);
            newPaymentDTO.setTransactionId(transactionId);
            newPaymentDTO.setPaymentStatus(PaymentStatus.COMPLETED);
            newPaymentDTO.setUpdatedAt(LocalDateTime.now());           
            paymentMapper.updatePaymentStatus(newPaymentDTO);
            
            // 예약 상태 CONFIRMED으로 업데이트
            ReqReservation.UpdateState updateStatus = new ReqReservation.UpdateState();
            updateStatus.reservationId = newPaymentDTO.getReservationId();
            updateStatus.status = ReservationStatus.CONFIRMED;
            reservationService.updateReservationForAdmin(updateStatus);
            
            // PayPal의 성공 URL을 그대로 반환
            //String paypalRedirectUrl = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + token;

            // PayPal의 URL을 클라이언트로 리디렉션
            //return ResponseEntity.status(HttpStatus.FOUND)
            //					 .location(URI.create(paypalRedirectUrl))
            //					 .build();
            return ResponseEntity.ok("결제가 성공되었습니다.");
            
        } catch (PayPalRESTException e) {
            // 에러 처리
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // 24.12.07 지은 [완료] : 결제 취소 처리 및 상태 업데이트 작업 완료
    // 파라미터 부분 paymentId에서 order의 id로 변경
    // 예약 내역, 주문서 내역, 결제 내역 상태 업데이트 작업 끝
    @GetMapping("/cancel")
    public String cancel(@RequestParam(name="paymentId") String paymentId) {
    	// order 객체 가져오기.
    	Order order = orderMapper.findByPaypalId(paymentId);
    	orderMapper.updateOrderStatus(paymentId, "CANCELLED");
    	
    	// 수정된 내용 결제 내역에(payment) 상태 업데이트
    	PaymentDTO newPaymentDTO = paymentMapper.getPaymentByOrderId(order.getId());
    	newPaymentDTO.setPaymentStatus(PaymentStatus.CANCELLED);
    	newPaymentDTO.setUpdatedAt(LocalDateTime.now());
    	paymentMapper.updatePaymentStatus(newPaymentDTO);
    	
    	ReqReservation.UpdateState updateStatus = new ReqReservation.UpdateState();
    	updateStatus.reservationId = newPaymentDTO.getReservationId();
    	updateStatus.status = ReservationStatus.PENDING;
    	reservationService.updateReservationForAdmin(updateStatus);
    	

    	return "결제가 취소 되었습니다.";
    }
}
