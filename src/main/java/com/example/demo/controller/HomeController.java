package com.example.demo.controller;

import com.example.demo.service.MailService;
import com.example.demo.model.HeroForm;
import com.example.demo.model.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private MailService mailService;

    @Value("${admin.mail.to}")
    private String adminTo;

    @Value("${admin.mail.cc}")
    private String adminCc;

    @PostMapping("/submitHero")
    public ResponseEntity<?> submitHero(@Valid @RequestBody HeroForm hero) {
        String msg = "New Enquiry:\nName: " + hero.getName() + "\nContact: " + hero.getContact();
        mailService.sendMail(adminTo, adminCc, "New Hero Form Submission", msg);
        return ResponseEntity.ok("Sent successfully");
    }

    @PostMapping("/popupSubmit")
    public ResponseEntity<?> popupSubmit(@Valid @RequestBody HeroForm hero) {
        String msg = "New Enquiry (Popup):\nName: " + hero.getName() + "\nContact: " + hero.getContact();
        mailService.sendMail(adminTo, adminCc, "Popup Form Submission", msg);
        return ResponseEntity.ok("Sent successfully");
    }

    @PostMapping("/contact")
    public ResponseEntity<?> submitContact(@Valid @RequestBody ContactForm contact) {
        String msg = "New Contact Form:\nName: " + contact.getName() +
                     "\nEmail: " + contact.getEmail() +
                     "\nPhone: " + contact.getPhone() +
                     "\nMessage: " + contact.getMessage();
        mailService.sendMail(adminTo, adminCc, "Contact Form Submission", msg);
        return ResponseEntity.ok("Sent successfully");
    }
}
