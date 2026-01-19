package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.QuotationRequest;

@Service
public class QuotationService {

    // 1. Fixed the variable name to mailService
    private final MailService mailService;

    // 2. Removed HomeController (Services should not call Controllers)
    public QuotationService(MailService mailService) {
        this.mailService = mailService;
    }

    public void processQuotation(QuotationRequest q) {
        String subject = "New Quotation Submitted";
        String body = "A new quotation has been submitted:\n\n" + 
                      "Property Type: " + q.getPropertyType() + "\n" + 
                      "City: " + q.getCity() + "\n" + 
                      "Unit Type: " + q.getUnitType() + "\n" + 
                      "Name: " + q.getName() + "\n" + 
                      "Mobile: " + q.getMobile() + "\n" + 
                      "Email: " + q.getEmail();

        String mainAdmin = "nayanmahajan2002@gmail.com";
        String[] ccList = {"comfortzone.ss24@gmail.com", "another-email@gmail.com"};
        
        // 3. Changed mailSender to mailService to match the constructor
        mailService.sendMail(mainAdmin, ccList, subject, body);
    }
}