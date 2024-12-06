package com.hjhotelback.controller.payment;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
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
import com.hjhotelback.dto.reservation.ResReservation;
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
@RequestMapping("/api/paypal")
@RequiredArgsConstructor
public class PayPalController {
    private final PayPalService payPalService;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;
    private final ReservationService reservationService;

    // 24.11.29 지은 [완료] : order(주문서), payment(결제내역) 생성 완료.
    @GetMapping("/checkout/{reservationId}")
    public String checkout(@PathVariable("reservationId") Integer reservationId) {
    	 try {
         	ReservationItem reservationItem = productMapper.findById(reservationId);
             if (reservationItem == null) {
                 throw new RuntimeException("Reservation not found");
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
                     "http://localhost:8080/api/paypal/cancel",
                     "http://localhost:8080/api/paypal/success"
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
    public String success(@RequestParam(name="paymentId") String paymentId, @RequestParam(name="PayerID") String payerID) {
    	System.out.println("Payment ID: " + paymentId);
    	System.out.println("Payer ID: " + payerID);
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
            
            System.out.println("여기까지 테스트"); //테스트 콘솔
            
            // orderId로 특정 결제내역 가져와서6 paymentId 가져오기.
            PaymentDTO newPaymentDTO = paymentMapper.getPaymentByOrderId(orderId);
            // transactionId, status, updatedAt 저장
            newPaymentDTO.setTransactionId(transactionId);
            newPaymentDTO.setPaymentStatus(PaymentStatus.COMPLETED);
            newPaymentDTO.setUpdatedAt(LocalDateTime.now());
           
            // 수정된 내용 업데이트
            paymentMapper.updatePaymentStatus(newPaymentDTO);
            
            // 예약 상태 CONFIRMED으로 업데이트
            ReqReservation.UpdateState updateStatus = new ReqReservation.UpdateState();
            updateStatus.reservationId = newPaymentDTO.getReservationId();
            updateStatus.status = ReservationStatus.CONFIRMED;
            
            reservationService.updateReservationForAdmin(updateStatus);
            
            return "success";
            
        } catch (PayPalRESTException e) {
            // 에러 처리
            return "error";
        }
    }
    
    // 24.12.05 지은 [완료] : 결제 취소 처리 및 상태 업데이트 작업 완료
    // 파라미터 부분 수정 작업 필요해서 수정함.
    // 예약 내역, 주문서 내역, 결제 내역 상태 업데이트 작업 끝
    @GetMapping("/cancel")
    public String cancel(@RequestParam(name="paymentId") Integer paymentId) {
    	
    	// 상태 변경 업데이트를 위해 payment 목록 가져오기.
    	PaymentDTO newPaymentDTO = paymentMapper.getPaymentById(paymentId);
    	newPaymentDTO.setPaymentStatus(PaymentStatus.CANCELLED);
        newPaymentDTO.setUpdatedAt(LocalDateTime.now());
    	
        // 수정된 내용 결제 내역에(payment) 상태 업데이트
        paymentMapper.updatePaymentStatus(newPaymentDTO);
        
        // payment에서 orderId 가져와서 주문서(orders) 상태 업데이트
        Order order = orderMapper.findByPaypalPkOrderId(newPaymentDTO.getOrderId());
        orderMapper.updateOrderStatus(order.getPaypalOrderId(), "CANCELLED");
        
        // 예약 상태 CONFIRMED으로 업데이트
        // 결제에서 취소되었는데 예약까지 취소시킬 필요는 없을 거 같음.
//        ReqReservation.UpdateState updateStatus = new ReqReservation.UpdateState();
//        updateStatus.reservationId = reservationId;
//        updateStatus.status = ReservationStatus.CANCELLED;
//        
//        reservationService.updateReservationForAdmin(updateStatus);
        
        return "cancel";
    }
}