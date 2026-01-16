package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.QuotationEntity;
import com.example.demo.model.QuotationRequest;
import com.example.demo.repository.QuotationRepository;
import com.example.demo.service.MailService;

@RestController
public class QuotationController {

    private final QuotationRepository quotationRepository;
    private final MailService mailService;

    public QuotationController(QuotationRepository quotationRepository, MailService mailService) {
        this.quotationRepository = quotationRepository;
        this.mailService = mailService;
    }

    @PostMapping("/quotation/submit")
    public ResponseEntity<?> submitQuotation(@RequestBody QuotationRequest request) {

        QuotationEntity entity = new QuotationEntity();
        entity.setName(request.getName());
        entity.setMobile(request.getMobile());
        entity.setEmail(request.getEmail());
        entity.setPropertyType(request.getPropertyType());
        entity.setCity(request.getCity());
        entity.setUnitType(request.getUnitType());

        quotationRepository.save(entity);

        String msg = "New Quotation Submission:\n"
                + "Name: " + request.getName() + "\n"
                + "Mobile: " + request.getMobile() + "\n"
                + "Email: " + request.getEmail() + "\n"
                + "Property: " + request.getPropertyType() + "\n"
                + "City: " + request.getCity() + "\n"
                + "Unit: " + request.getUnitType();

        mailService.sendMail("comfortzone.ss24@gmail.com",
                             "nayanmahajan2002@gmail.com",
                             "New Quotation Submission",
                             msg);

        return ResponseEntity.ok(Map.of("status", "success", "message", "Quotation saved successfully"));
    }
}
