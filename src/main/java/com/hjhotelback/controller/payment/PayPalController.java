package com.hjhotelback.controller.payment;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.payment.Order;
import com.hjhotelback.dto.payment.ReservationItem;
import com.hjhotelback.mapper.paypal.OrderMapper;
import com.hjhotelback.mapper.paypal.ProductMapper;
import com.hjhotelback.service.payment.PayPalService;
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

    // 24.11.29 지은 [작업중] : 현재 Order DTO는 orders 테이블과 연결이 된 상태. payment 테이블로 다시 매핑 해야한다.
    @GetMapping("/checkout/{reservationId}")
    public String checkout(@PathVariable("reservationId") Integer reservationId) {
    	 try {
         	ReservationItem reservationItem = productMapper.findById(reservationId);
             if (reservationItem == null) {
                 throw new RuntimeException("Product not found");
             }
             Order order = new Order();
             order.setProductId(reservationId);
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
    
    // 24.11.29 지은 [완료] : PayerId를 payerId로 변경
    @GetMapping("/success")
    public String success(@RequestParam(name="paymentId") String paymentId, @RequestParam(name="PayerID") String payerID) {
    	System.out.println("Payment ID: " + paymentId);
    	System.out.println("Payer ID: " + payerID);
        try {
            Payment payment = payPalService.executePayment(paymentId, payerID);
            orderMapper.updateStatus(paymentId, "COMPLETED");
            return "success";
        } catch (PayPalRESTException e) {
            // 에러 처리
            return "error";
        }
    }
    @GetMapping("/cancel")
    public String cancel(@RequestParam(name="paymentId") String paymentId) {
        orderMapper.updateStatus(paymentId, "CANCELLED");
        return "cancel";
    }
}
