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
import com.hjhotelback.mapper.paypal.OrderMapper;
import com.hjhotelback.mapper.paypal.ProductMapper;
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
    @Transactional
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
    
    // 24.12.01 지은 [완료] : 결제 성공 처리 및 상태 업데이트 작업 완료
    // 예약 내역, 주문서 내역, 결제 내역 상태 업데이트 작업 끝
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
            
            // 결제 완료된 후 주문 상태 업데이트
            orderMapper.updateOrderStatus(paymentId, "COMPLETED");
           
            // PaypalOrderId로 reservationId 가져오기
            String PaypalOrderId = payment.getId();
            Order order = orderMapper.findByPaypalOrderId(PaypalOrderId);
            Integer reservationId = order.getReservationId();
            
            // 예약ID로 DB에 저장된 특정 결제내역 가져와서 transactionId, status, updatedAt 저장
            PaymentDTO newPaymentDTO = paymentMapper.getPaymentById(reservationId);
            newPaymentDTO.setTransactionId(transactionId);
            newPaymentDTO.setPaymentStatus(PaymentStatus.COMPLETED);
            newPaymentDTO.setUpdatedAt(LocalDateTime.now());
           
            // 수정된 내용 업데이트
            paymentMapper.updatePaymentStatus(newPaymentDTO);
            
            // 예약 상태 CONFIRMED으로 업데이트
            ReqReservation.UpdateState updateStatus = new ReqReservation.UpdateState();
            updateStatus.reservationId = reservationId;
            updateStatus.status = ReservationStatus.CONFIRMED;
            
            reservationService.updateReservationForAdmin(updateStatus);
            
            return "success";
            
        } catch (PayPalRESTException e) {
            // 에러 처리
            return "error";
        }
    }
    
    // 24.12.01 지은 [완료] : 결제 취소 처리 및 상태 업데이트 작업 완료
    // 예약 내역, 주문서 내역, 결제 내역 상태 업데이트 작업 끝
    @GetMapping("/cancel")
    public String cancel(@RequestParam(name="paymentId") Integer paymentId) {
    	
    	// payment 목록 가져와서 reservation id 가져오기
    	PaymentDTO newPaymentDTO = paymentMapper.getPaymentById(paymentId);
    	Integer reservationId = newPaymentDTO.getReservationId();
    	newPaymentDTO.setPaymentStatus(PaymentStatus.CANCELLED);
        newPaymentDTO.setUpdatedAt(LocalDateTime.now());
    	
        // 수정된 내용 결제 내역에(payment) 상태 업데이트
        paymentMapper.updatePaymentStatus(newPaymentDTO);
        
        // paypalOrderId 가져와서 주문서(orders) 상태 업데이트
        Order order = orderMapper.findByPaypalReservationId(reservationId);
        String paypalOrderId = order.getPaypalOrderId();
        orderMapper.updateOrderStatus(paypalOrderId, "CANCELLED");
        
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
