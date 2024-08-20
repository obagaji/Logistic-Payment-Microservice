package com.jtc.Logistic.Payment.payment;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Configs {


    @Bean
    public JavaMailSender javaMailSender()
    {
        return new JavaMailSenderImpl();
    }
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

}
