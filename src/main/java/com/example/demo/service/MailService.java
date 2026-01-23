package com.example.demo.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Properties;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    @Value("${gmail.client-id}") private String clientId;
    @Value("${gmail.client-secret}") private String clientSecret;
    @Value("${gmail.refresh-token}") private String refreshToken;
    @Value("${gmail.email}") private String fromEmail;

    @Value("${app.admin.email}") String mainAdmin; 
    @Value("${app.admin.cc}") String ccValue;
	
    public void sendMail(String to, String[] cc, String subject, String bodyText) {
        try {
            // 1. Build Credentials
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setTransport(GoogleNetHttpTransport.newTrustedTransport())
                    .setJsonFactory(GsonFactory.getDefaultInstance())
                    .setClientSecrets(clientId, clientSecret)
                    .build()
                    .setRefreshToken(refreshToken);

            // 2. Initialize Gmail Service
            Gmail service = new Gmail.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    credential)
                    .setApplicationName("InteriorApp")
                    .build();

            // 3. Create Email
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage email = new MimeMessage(session);
            email.setFrom(new InternetAddress(fromEmail));
            email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
            
            if (cc != null) {
                for (String ccEmail : cc) {
                    email.addRecipient(jakarta.mail.Message.RecipientType.CC, new InternetAddress(ccEmail));
                }
            }
            email.setSubject(subject);
            email.setText(bodyText);

            // 4. Encode and Send
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);
            byte[] rawMessageBytes = buffer.toByteArray();
            String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
            Message message = new Message();
            message.setRaw(encodedEmail);

            service.users().messages().send("me", message).execute();
            System.out.println("✅ Gmail API Sent Successfully!");

        } catch (Exception e) {
            System.err.println("❌ Gmail API Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

