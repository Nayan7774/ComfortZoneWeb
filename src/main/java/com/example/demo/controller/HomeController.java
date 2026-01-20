package com.example.demo.controller;

import com.example.demo.model.ContactForm;
import com.example.demo.model.HeroForm;
import com.example.demo.service.MailService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HomeController {

    private final MailService mailService;

    public HomeController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/submitHero")
    public ResponseEntity<?> submitHero(@Valid @RequestBody HeroForm hero) {
        try {
            String subject = "New Enquiry Form Submission";
            String body = "New Enquiry on website:\nName: " + hero.getName() +
                    "\nContact: " + hero.getContact();

            mailService.sendMail("comfortzone.ss24@gmail.com", "nayanmahajan2002@gmail.com", subject, body);
            return ResponseEntity.ok(Map.of("status", "success", "message", "Hero form sent successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "error", "message", "Email service unavailable"));
        }
    }

    @PostMapping("/popupSubmit")
    public ResponseEntity<?> popupSubmit(@Valid @RequestBody HeroForm hero) {
        try {
            String subject = "New Popup Form Submission";
            String body = "New Enquiry on website:\nName: " + hero.getName() +
                    "\nContact: " + hero.getContact();

            mailService.sendMail("comfortzone.ss24@gmail.com", "nayanmahajan2002@gmail.com", subject, body);
            return ResponseEntity.ok(Map.of("status", "success", "message", "Popup form sent successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "error", "message", "Email service unavailable"));
        }
    }

    @PostMapping("/contact")
    public ResponseEntity<?> submitContact(@Valid @RequestBody ContactForm contact) {
        try {
            String subject = "New Contact Form Submission";
            String body = "New Contact Form Submission:\nName: " + contact.getName() +
                    "\nEmail: " + contact.getEmail() +
                    "\nPhone: " + contact.getPhone() +
                    "\nMessage: " + contact.getMessage();

            mailService.sendMail("comfortzone.ss24@gmail.com", "nayanmahajan2002@gmail.com", subject, body);
            return ResponseEntity.ok(Map.of("status", "success", "message", "Contact form sent successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "error", "message", "Email service unavailable"));
        }
    }
}
