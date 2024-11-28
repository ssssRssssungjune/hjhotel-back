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
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PayPalController {
    private final PayPalService payPalService;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;

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
                     "http://localhost:8080/pay/cancel",
                     "http://localhost:8080/pay/success"
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
             // 에러 처리
             return "error";
         }
    	

    }
    @GetMapping("/success")
    public String success(@RequestParam String paymentId, @RequestParam String PayerID) {
    	System.out.println("Payment ID: " + paymentId);
    	System.out.println("Payer ID: " + PayerID);
        try {
            Payment payment = payPalService.executePayment(paymentId, PayerID);
            orderMapper.updateStatus(paymentId, "COMPLETED");
            return "success";
        } catch (PayPalRESTException e) {
            // 에러 처리
            return "error";
        }
    }
    @GetMapping("/cancel")
    public String cancel(@RequestParam String paymentId) {
        orderMapper.updateStatus(paymentId, "CANCELLED");
        return "cancel";
    }
}
