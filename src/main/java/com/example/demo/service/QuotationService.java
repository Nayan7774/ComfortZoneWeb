package com.example.demo.service;

import com.example.demo.model.QuotationRequest;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class QuotationService {

    private final MailService mailService;
 
    @Value("${spring.mail.username}")
    
    private final String mainAdmin;
    
    @Value("${admin.mail.cc:}")
    private String[] ccList;


    public QuotationService(MailService mailService,
                            @Value("${admin.mail.to}") String mainAdmin,
                            @Value("${admin.mail.cc}") String[] ccList) {
        this.mailService = mailService;
        this.mainAdmin = mainAdmin;
        this.ccList = ccList;
    }

    // Async email sending prevents blocking your API
    @Async
    public void processQuotation(QuotationRequest q) {
        try {
            String subject = "New Quotation Submitted";
            String body =
                    "Property Type: " + q.getPropertyType() + "\n" +
                    "City: " + q.getCity() + "\n" +
                    "Unit Type: " + q.getUnitType() + "\n" +
                    "Name: " + q.getName() + "\n" +
                    "Mobile: " + q.getMobile() + "\n" +
                    "Email: " + q.getEmail();

            mailService.sendMail(mainAdmin, ccList, subject, body);

        } catch (Exception e) {
            System.err.println("❌ Quotation mail async failed");
            e.printStackTrace();
        }
    }
}
