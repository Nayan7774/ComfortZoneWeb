package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.QuotationRequest;
import com.example.demo.service.MailService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class QuotationController{

    private final MailService mailService;

    // Removed QuotationRepository from constructor
    public QuotationController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/submitQuotation")
    public ResponseEntity<?> submitQuotation(@Valid @RequestBody QuotationRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error", "message", result.getAllErrors().get(0).getDefaultMessage()));
        }

        // Removed DB mapping and save
        try {
        	
      
        String msg = "New Quotation Submission:\n" +
                     "Name: " + request.getName() + "\n" +
                     "Mobile: " + request.getMobile() + "\n" +
                     "Email: " + request.getEmail() + "\n" +
                     "Property: " + request.getPropertyType() + "\n" +
                     "City: " + request.getCity() + "\n" +
                     "Unit: " + request.getUnitType();
        // Send the email
        mailService.sendMail("comfortzone.ss24@gmail.com",
                             "nayanmahajan2002@gmail.com",
                             "New Quotation Submission",
                             msg);

        // Changed message to "sent" instead of "saved"
        return ResponseEntity.ok(Map.of("status", "success", "message", "Quotation sent successfully"));
    }
        catch (Exception e) 
        {
    	 return ResponseEntity.internalServerError()
                 .body(Map.of("status", "error",
                              "message", "Email service unavailable"));
	}
        
    }
}