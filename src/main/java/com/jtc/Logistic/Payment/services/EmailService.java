package com.jtc.Logistic.Payment.services;

import com.jtc.Logistic.Payment.emailInterface.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService implements EmailSender
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
/*    @Value("${}")
    private String from;

    @Value("${}")
    private String host;*/
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JavaMailSender javaMailSender;

    public String getUserEmailAddress(String userName)
    {
        return null; //restTemplate.getForObject("http://localhost/8000/" + userName, String.class);

    }

    @Override
    public void sendEmailWithAttachment(String name, String to, String message) {

    }

    @Override
    public void sendEmailForNotification(String name, String to, String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("messaging you");
            mailMessage.setTo(getUserEmailAddress(to));
            mailMessage.setText("thanks for your patronige");
            javaMailSender.send(mailMessage);
        }
        catch (Exception exception)
        {
            throw  new RuntimeException(exception.getMessage());
        }

    }
}
