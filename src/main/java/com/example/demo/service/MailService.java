package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String cc,String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

	public void sendMail(String string, String subject, String body) {
		// TODO Auto-generated method stub
		
	}
}
