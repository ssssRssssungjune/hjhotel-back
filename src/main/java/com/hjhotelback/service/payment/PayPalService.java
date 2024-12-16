package com.hjhotelback.service.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PayPalService {
    private final APIContext apiContext;
    public Payment createPayment(
            BigDecimal total,
            String currency,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {
    	
    	try {
    		Amount amount = new Amount();
    		amount.setCurrency(currency);
    		amount.setTotal(total.toString());
    		Transaction transaction = new Transaction();
    		transaction.setDescription(description);
    		transaction.setAmount(amount);
    		List<Transaction> transactions = new ArrayList<>();
    		transactions.add(transaction);
    		Payment payment = new Payment();
    		payment.setIntent("sale");
    		payment.setPayer(new Payer());
    		payment.getPayer().setPaymentMethod("paypal");
    		payment.setTransactions(transactions);
    		RedirectUrls redirectUrls = new RedirectUrls();
    		redirectUrls.setCancelUrl(cancelUrl);
    		redirectUrls.setReturnUrl(successUrl);
    		payment.setRedirectUrls(redirectUrls);
    		return payment.create(apiContext);	
    	} catch(PayPalRESTException e) { // 더 상세한 에러를 얻기 위해 추가.
    		System.err.println("PayPal error: " + e.getDetails());
    		throw e;
    	}
    }
    public Payment executePayment(String paymentId, String payerId)
            throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecution);
    }
}