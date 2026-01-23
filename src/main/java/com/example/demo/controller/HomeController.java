package com.example.demo.controller;

import com.example.demo.model.ContactForm;
import com.example.demo.model.HeroForm;
import com.example.demo.service.MailService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HomeController {

    private final MailService mailService;
    private final String adminTo;
    private final String[] adminCc;

    public HomeController(
            MailService mailService,
            @Value("${app.admin.email}") String mainAdmin, 
            @Value("${app.admin.cc}") String ccValue) {
		
    	/*
		 * @Value("${app.admin.email}") String adminTo,
		 * 
		 * @Value("${app.admin.cc}") String adminCc) {
		 */
        this.mailService = mailService;
        this.adminTo = mainAdmin;
        
        // Safety check: ensure we don't pass empty strings to the mailer
        if (ccValue == null || ccValue.isBlank()) {
            this.adminCc = new String[0];
        } else {
            this.adminCc = Arrays.stream(ccValue.split(","))
                                 .map(String::trim)
                                 .filter(s -> !s.isEmpty())
                                 .toArray(String[]::new);
        }
    }
    
    @PostMapping("/submitHero")
    public ResponseEntity<?> submitHero(@Valid @RequestBody HeroForm hero) {

        String msg = "New Enquiry:\n"
                + "Name: " + hero.getName()
                + "\nContact: " + hero.getContact();

        try {
            mailService.sendMail(adminTo, adminCc, "New Hero Form Submission", msg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "fail", "error", e.getMessage()));
        }

        return ResponseEntity.ok(Map.of("status", "success"));
    }

    @PostMapping("/popupSubmit")
    public ResponseEntity<?> popupSubmit(@Valid @RequestBody HeroForm hero) {

        String msg = "New Enquiry (Popup):\n"
                + "Name: " + hero.getName()
                + "\nContact: " + hero.getContact();

        try {
            mailService.sendMail(adminTo, adminCc, "Popup Form Submission", msg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "fail", "error", e.getMessage()));
        }

        return ResponseEntity.ok(Map.of("status", "success"));
    }

    @PostMapping("/contact")
    public ResponseEntity<?> submitContact(@Valid @RequestBody ContactForm contact) {

        String msg = "New Contact Form:\n"
                + "Name: " + contact.getName()
                + "\nEmail: " + contact.getEmail()
                + "\nPhone: " + contact.getPhone()
                + "\nMessage: " + contact.getMessage();

        try {
            mailService.sendMail(adminTo, adminCc, "Contact Form Submission", msg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "fail", "error", e.getMessage()));
        }

        return ResponseEntity.ok(Map.of("status", "success"));
    }
}
