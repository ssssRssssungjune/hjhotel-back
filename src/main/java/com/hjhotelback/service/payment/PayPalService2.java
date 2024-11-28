package com.hjhotelback.service.payment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PayPalService2 {

    private final APIContext apiContext;

    public PayPalService2(@Value("${paypal.client.id}") String clientId,
                         @Value("${paypal.client.secret}") String clientSecret,
                         @Value("${paypal.mode}") String mode) {
        this.apiContext = new APIContext(clientId, clientSecret, mode);
    }

    // PayPal 결제 생성
    public Payment createPayment(double total, String currency, String method,
                                 String intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException {

        // 결제 정보 구성
        com.paypal.api.payments.Amount amount = new com.paypal.api.payments.Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total)); // 결제 금액 설정

        com.paypal.api.payments.Transaction transaction = new com.paypal.api.payments.Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<com.paypal.api.payments.Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        // Payer(결제자) 설정
        com.paypal.api.payments.Payer payer = new com.paypal.api.payments.Payer();
        payer.setPaymentMethod(method); // "paypal"로 설정

        // 결제 요청 정보 생성
        com.paypal.api.payments.Payment payment = new com.paypal.api.payments.Payment();
        payment.setIntent(intent); // "sale"로 설정
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        // 승인 및 취소 URL 설정
        com.paypal.api.payments.RedirectUrls redirectUrls = new com.paypal.api.payments.RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        // PayPal API를 호출하여 결제 생성
        return payment.create(apiContext);
    }

    // PayPal 결제 캡처(승인 완료 처리)
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        return payment.execute(apiContext, new com.paypal.api.payments.PaymentExecution().setPayerId(payerId));
    }
}