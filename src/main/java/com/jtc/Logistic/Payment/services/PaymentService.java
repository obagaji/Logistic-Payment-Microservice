package com.jtc.Logistic.Payment.services;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final APIContext apiContext;

    public Payment payment(Double payAmount,
                           String currency,
                           String method,
                           String intent,
                           String description,
                           String cancelUrl,
                           String successUrl) throws PayPalRESTException {
        // create an amount object. to be able to set the currency and format the total amount.
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency),"%.2f",payAmount));
        // the other step is to set the transaction, where the amount object is set
        // and the description of the transaction. we are also creating an arraylist of the set of transaction made.
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(description);
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        // the next step will be to create a payer object where the paymentmethod is set .
        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment(intent, payer);
        payment.setTransactions(transactionList);

        // creating the redirectUrl
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);

    }

    public Payment paymentExecution(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        payment.execute(apiContext,paymentExecution);
        return payment;
    }

}
