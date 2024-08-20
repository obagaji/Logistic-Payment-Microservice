package com.jtc.Logistic.Payment.paymentController;


import com.jtc.Logistic.Payment.services.EmailService;
import com.jtc.Logistic.Payment.services.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PayController {

    private final PaymentService paymentService;

    private final EmailService emailService;
    @Autowired
    private RestTemplate restTemplate;

    private Logger LOGGER = LoggerFactory.getLogger(PayController.class);

    @GetMapping("/")
    public ResponseEntity<String> getPaymentPage()
    {
        String response = restTemplate.getForObject(
                "http://localhost:9050/commodity/package/view",
                String.class);
        return new ResponseEntity<>("Welcome to the payment platform for paypal : " + response, HttpStatusCode.valueOf(HttpStatus.SC_OK));
    }
    @GetMapping("/make/sale")
    public RedirectView getTransaction()
    {
        try {
            String cancelUrl = "http://localhost:9888/payment/cancel";
            String sucessful = "http://localhost:9888/payment/sucessfull";
           Payment payment= paymentService.payment(10.01,
                    "USD",
                    "paypal",
                    "sale",
                    "make new sales",
                    cancelUrl,
                    sucessful);

             for (Links links: payment.getLinks())
             {
               if (links.getRel().equals("approval_url"))
               {
                   return  new RedirectView( links.getHref()) ;
               }
             }
        }
        catch (PayPalRESTException e)
        {
            LOGGER.error(e.getMessage());
        }
        return new RedirectView( "http://localhost:9888/payment/error");
    }

    @PostMapping("/successful")
    public ResponseEntity<String> getSuccessfulPayment(@RequestParam("paymentId") String paymentId,
                                                       @RequestParam("PayerId") String payerId)
    {
        LOGGER.info(" paymentId : " + paymentId);
        LOGGER.info(" payerId : " + payerId);
        try{
            Payment payment = paymentService.paymentExecution(paymentId,payerId);
            if (payment.getState().equals("approval_url"))
            {
                return new ResponseEntity<>("payment successful", HttpStatusCode.valueOf(201));
            }
        }
        catch(PayPalRESTException exception)
        {
            LOGGER.error("error message : " + exception);
        }
        return new ResponseEntity<>("something went wrong",HttpStatusCode.valueOf(500));
    }

    @GetMapping("/cancel")
    public ResponseEntity<String>cancelTransaction()
    {
        return new ResponseEntity<>("Payment Cancelled", HttpStatusCode.valueOf(200));
    }
    @GetMapping("/error")
    public ResponseEntity<String>errorTransaction()
    {
        return new ResponseEntity<>("Error in Transaction", HttpStatusCode.valueOf(200));
    }

}
