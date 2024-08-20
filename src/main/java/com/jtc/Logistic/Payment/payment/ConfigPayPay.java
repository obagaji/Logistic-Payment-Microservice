package com.jtc.Logistic.Payment.payment;


import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigPayPay {

    @Value("${paypal.clientID}")
    private String clientId;
    @Value("${paypal.client-secret}")
    private String client_secret;
    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public APIContext apiContext()
    {
        return new APIContext(clientId,client_secret,mode);
    }


}
