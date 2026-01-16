
package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.ContactForm;
import com.example.demo.model.HeroForm;
import com.example.demo.model.QuotationEntity;
import com.example.demo.model.QuotationRequest;
import com.example.demo.repository.ContactFormRepository;
import com.example.demo.repository.HeroFormRepository;
import com.example.demo.repository.QuotationRepository;
import com.example.demo.service.MailService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")   // a`llow frontend
@RestController
@RequestMapping("/api")
public class HomeController {

    private final QuotationRepository quotationRepository;

	@Autowired
	private MailService mailService;
	
	
    @Autowired
    private HeroFormRepository heroRepo;

    @Autowired
    private ContactFormRepository contactRepo;

    HomeController(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    // HERO FORM: /api/submitHero
    @PostMapping("/submitHero")
    public ResponseEntity<?> submitHero(@Valid @RequestBody HeroForm hero, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().get(0).getDefaultMessage());
        }
        heroRepo.save(hero);
        
        String msg = "New Enquiry on website :\nName: " + hero.getName() + "\nContact: " + hero.getContact();
        mailService.sendMail("comfortzone.ss24@gmail.com", "nayanmahajan2002@gmail.com", "New Enquiry Form Submission", msg);
        //comfortzone.ss24@gmail.com
        return ResponseEntity.ok("Saved successfully");
    }

    @PostMapping("/popupSubmit")
    public ResponseEntity<?> popupSubmit(@Valid @RequestBody HeroForm hero, BindingResult result) {
        // Use the same logic as hero form
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().get(0).getDefaultMessage());
        }
        heroRepo.save(hero);
        
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
        contactRepo.save(contact);
       
        String msg = "New Contact Form Submission:\nName: " + contact.getName() +
                "\nEmail: " + contact.getEmail() +
                "\nPhone: " + contact.getPhone() +
                "\nMessage: " + contact.getMessage();
         mailService.sendMail("comfortzone.ss24@gmail.com", "nayanmahajan2002@gmail.com" , "New Contact Form Submission", msg);
        ///comfortzone.ss24@gmail.com
        return ResponseEntity.ok("Saved successfully");
    }

    
    @PostMapping("/submitQuotation")
    public ResponseEntity<?> submitQuotation(@Valid @RequestBody QuotationRequest request, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error", "message", result.getAllErrors().get(0).getDefaultMessage()));
        }

        // Map DTO -> Entity
        QuotationEntity entity = new QuotationEntity();
        entity.setName(request.getName());
        entity.setMobile(request.getMobile());
        entity.setEmail(request.getEmail());
        entity.setPropertyType(request.getPropertyType());
        entity.setCity(request.getCity());
        entity.setUnitType(request.getUnitType());

        // Save to DB
        quotationRepository.save(entity);

        // Optional: Send email
        String msg = "New Quotation Submission:\n" +
                     "Name: " + request.getName() + "\n" +
                     "Mobile: " + request.getMobile() + "\n" +
                     "Email: " + request.getEmail() + "\n" +
                     "Property: " + request.getPropertyType() + "\n" +
                     "City: " + request.getCity() + "\n" +
                     "Unit: " + request.getUnitType();

        mailService.sendMail("comfortzone.ss24@gmail.com", "nayanmahajan2002@gmail.com",
                "New Quotation Submission", msg);

        return ResponseEntity.ok(Map.of("status", "success", "message", "Quotation saved successfully"));
    }
}

