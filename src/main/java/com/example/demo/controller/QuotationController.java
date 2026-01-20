package com.example.demo.controller;

import com.example.demo.model.QuotationRequest;
import com.example.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuotationController {

    @Autowired
    private MailService mailService;

    @Value("${admin.mail.to}")
    private String adminTo;

    @Value("${admin.mail.cc}")
    private String adminCc;

    @PostMapping("/submitQuotation")
    public ResponseEntity<?> submitQuotation(@Valid @RequestBody QuotationRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    Map.of("status", "error", "message", result.getAllErrors().get(0).getDefaultMessage())
            );
        }

        String msg = "New Quotation Submission:\n" +
                     "Name: " + request.getName() + "\n" +
                     "Mobile: " + request.getMobile() + "\n" +
                     "Email: " + request.getEmail() + "\n" +
                     "Property: " + request.getPropertyType() + "\n" +
                     "City: " + request.getCity() + "\n" +
                     "Unit: " + request.getUnitType();

        mailService.sendMail(adminTo, adminCc, "New Quotation Submission", msg);

        return ResponseEntity.ok(Map.of("status", "success", "message", "Quotation sent successfully"));
    }
}
