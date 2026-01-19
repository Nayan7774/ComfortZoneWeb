package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    // This is the version your Controller is likely calling
    public void sendMail(String to, String cc, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nayanmahajan2002@gmail.com");
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(text);
        
        mailSender.send(message);
    }

    // This version handles multiple CC recipients (3+)
    public void sendMail(String to, String[] ccList, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nayanmahajan2002@gmail.com");
        message.setTo(to);
        message.setCc(ccList); 
        message.setSubject(subject);
        message.setText(text);
        
        mailSender.send(message);
    }
}