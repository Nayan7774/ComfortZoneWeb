package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.request.QuotationRequest;

@Service
public class QuotationService {

    private final MailService mailService;
    private final String mainAdmin;
    private final String[] ccList;

    public QuotationService(
            MailService mailService,
            @Value("${mail.target.to}") String mainAdmin, 
            @Value("${mail.target.cc}") String ccValue) {

        this.mailService = mailService;
        this.mainAdmin = mainAdmin;
        this.ccList = (ccValue == null || ccValue.isBlank())
                ? new String[0]
                : ccValue.split(",");
    }

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
            System.out.println("✅ Quotation email sent");

        } catch (Exception e) {
            System.err.println("❌ Quotation mail async failed");
            e.printStackTrace();
        }
        
        
    }
}
