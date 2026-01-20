package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.demo.model.QuotationRequest;
@Service
public class QuotationService {

    private final MailService mailService;

    @Value("${admin.mail.to}")
    private String adminTo;

    @Value("${admin.mail.cc}")
    private String[] adminCc;

    public QuotationService(MailService mailService) {
        this.mailService = mailService;
    }

    public void processQuotation(QuotationRequest q) {

        String subject = "New Quotation Submitted";

        String body = """
                A new quotation has been submitted:

                Name: %s
                Mobile: %s
                Email: %s
                Property Type: %s
                City: %s
                Unit Type: %s
                """.formatted(
                    q.getName(),
                    q.getMobile(),
                    q.getEmail(),
                    q.getPropertyType(),
                    q.getCity(),
                    q.getUnitType()
                );

        mailService.sendMail(adminTo, adminCc, subject, body);
    }
}
