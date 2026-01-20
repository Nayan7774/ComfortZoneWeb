package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Send mail with single CC
    @Async
    public void sendMail(String to, String cc, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    // Send mail with multiple CCs
    @Async
    public void sendMail(String to, String[] ccList, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setCc(ccList);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
