package com.example.demo.service;

import com.example.demo.model.QuotationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class QuotationService {

    private final MailService mailService;
    private final String mainAdmin;
    private final String[] ccList;

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
        String subject = "New Quotation Submitted";
        String body = "A new quotation has been submitted:\n\n" +
                "Property Type: " + q.getPropertyType() + "\n" +
                "City: " + q.getCity() + "\n" +
                "Unit Type: " + q.getUnitType() + "\n" +
                "Name: " + q.getName() + "\n" +
                "Mobile: " + q.getMobile() + "\n" +
                "Email: " + q.getEmail();

        mailService.sendMail(mainAdmin, ccList, subject, body);
    }
}
