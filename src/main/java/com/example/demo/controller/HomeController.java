
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.ContactForm;
import com.example.demo.model.HeroForm;
import com.example.demo.service.MailService;

import jakarta.validation.Valid;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class HomeController {

	 @GetMapping("/")
	    public String index() {
	        return "index.html";
	    }
	
	
    @Autowired
    private MailService mailService;

    // 1. Removed all Repositories (heroRepo, contactRepo, quotationRepository)

    @PostMapping("/submitHero")
    public ResponseEntity<?> submitHero(@Valid @RequestBody HeroForm hero, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().get(0).getDefaultMessage());
        }
        
        // Removed heroRepo.save(hero);
        
        String msg = "New Enquiry on website :\nName: " + hero.getName() + "\nContact: " + hero.getContact();
        mailService.sendMail("comfortzone.ss24@gmail.com", "nayanmahajan2002@gmail.com", "New Enquiry Form Submission", msg);
        
        return ResponseEntity.ok("Sent successfully");
    }
   
    @PostMapping("/popupSubmit")
    public ResponseEntity<?> popupSubmit(@Valid @RequestBody HeroForm hero, BindingResult result) {
        // Use the same logic as hero form
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().get(0).getDefaultMessage());
        }
        
        String msg = "New Enquiry on website :\nName: " + hero.getName() + "\nContact: " + hero.getContact();
        mailService.sendMail("comfortzone.ss24@gmail.com", "nayanmahajan2002@gmail.com", "New Enquiry Form Submission", msg);
        //comfortzone.ss24@gmail.com
        return ResponseEntity.ok("Saved successfully");
    	
    }
      
    
    @PostMapping("/contact")
    public ResponseEntity<?> submitContact(@Valid @RequestBody ContactForm contact, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().get(0).getDefaultMessage());
        }
        
        // Removed contactRepo.save(contact);
       
        String msg = "New Contact Form Submission:\nName: " + contact.getName() +
                "\nEmail: " + contact.getEmail() +
                "\nPhone: " + contact.getPhone() +
                "\nMessage: " + contact.getMessage();
        mailService.sendMail("comfortzone.ss24@gmail.com", "nayanmahajan2002@gmail.com" , "New Contact Form Submission", msg);
        
        return ResponseEntity.ok("Sent successfully");
    }

  
}

