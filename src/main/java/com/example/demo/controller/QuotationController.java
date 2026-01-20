package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.QuotationRequest;
import com.example.demo.service.QuotationService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuotationController {

    private final QuotationService quotationService;

    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @PostMapping("/submitQuotation")
    public ResponseEntity<?> submitQuotation(
            @Valid @RequestBody QuotationRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error",
                            "message", result.getAllErrors().get(0).getDefaultMessage()));
        }

        try {
            quotationService.processQuotation(request);
            return ResponseEntity.ok(
                    Map.of("status", "success",
                           "message", "Quotation sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "error",
                                 "message", "Email service unavailable"));
        }
    }
}
