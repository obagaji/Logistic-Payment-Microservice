package com.jtc.Logistic.Payment.emailInterface;

public interface EmailSender {

    void sendEmailWithAttachment(String name,String to, String message);
    void sendEmailForNotification(String name, String to, String message);
}
