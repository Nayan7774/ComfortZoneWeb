package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String fromEmail;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String[] cc, String subject, String text) {

        // 🔒 Safety check – prevents startup/runtime crash
        if (fromEmail == null || fromEmail.isBlank()) {
            System.err.println("❌ MAIL_USER not configured. Mail skipped.");
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setReplyTo(fromEmail);
            message.setTo(to);

            if (cc != null && cc.length > 0) {
                message.setCc(cc);
            }

            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            System.out.println("✅ Mail sent successfully");

        } catch (Exception e) {
            System.err.println("❌ Mail sending failed");
            e.printStackTrace();
        }
    }
}
