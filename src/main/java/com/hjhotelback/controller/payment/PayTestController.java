package com.hjhotelback.controller.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.service.payment.PayPalService2;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/api/test/payment")
public class PayTestController {
	@Autowired
    private PayPalService2 payPalService;

    private static final String SUCCESS_URL = "http://localhost:8080/api/payment/success";
    private static final String CANCEL_URL = "http://localhost:8080/api/payment/cancel";

    @PostMapping
    public String createPayment(@RequestParam double amount) {
        try {
            Payment payment = payPalService.createPayment(
                    amount, "USD", "paypal",
                    "sale", "Payment description",
                    CANCEL_URL, SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/success")
    public String successPayment(@RequestParam("paymentId") String paymentId,
                                 @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "Payment successful!";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "Payment failed!";
    }

    @GetMapping("/cancel")
    public String cancelPayment() {
        return "Payment cancelled!";
    }
}
