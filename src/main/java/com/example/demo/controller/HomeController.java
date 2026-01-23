package com.example.demo.controller;

import com.example.demo.model.ContactForm;
import com.example.demo.model.HeroForm;
import com.example.demo.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
            @Value("${app.admin.email}") String adminTo,
            @Value("${app.admin.cc}") String adminCc) {

        this.mailService = mailService;
        this.adminTo = adminTo;
        this.adminCc = adminCc != null && !adminCc.isBlank()
                ? adminCc.split(",")
                : new String[0];
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
