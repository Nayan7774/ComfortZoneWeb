package com.example.demo.controller;

import com.example.demo.request.QuotationRequest;
import com.example.demo.service.QuotationService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Map;
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
            return ResponseEntity.badRequest().body(
                Map.of(
                    "status", "error",
                    "message", result.getAllErrors().get(0).getDefaultMessage()
                )
            );
        }

        // Async, fail-safe
        quotationService.processQuotation(request);

        return ResponseEntity.ok(
            Map.of("status", "success", "message", "Quotation submitted")
        );
    }
}
