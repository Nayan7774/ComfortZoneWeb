package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.QuotationRequest;
import com.example.demo.service.QuotationService;

@RestController
@RequestMapping("/api")
public class QuotationController {

    private final QuotationService quotationService;

    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @PostMapping("/submitQuotation")
    public ResponseEntity<?> submitQuotation(@RequestBody QuotationRequest request) {
        try {
            quotationService.processQuotation(request);  // async
            return ResponseEntity.ok(Map.of("status", "success", "message", "Quotation sent successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "error", "message", "Email service unavailable"));
        }
    }
}


