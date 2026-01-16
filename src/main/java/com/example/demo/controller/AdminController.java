package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.repository.QuotationRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // Hardcoded credentials (for beginner setup)
    private final String ADMIN_USER = "admin";
    private final String ADMIN_PASS = "admin123";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> payload, HttpSession session){
        String username = payload.get("username");
        String password = payload.get("password");

        if(ADMIN_USER.equals(username) && ADMIN_PASS.equals(password)){
            session.setAttribute("ADMIN_LOGGED_IN", true);
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkLogin(HttpSession session){
        Boolean loggedIn = (Boolean) session.getAttribute("ADMIN_LOGGED_IN");
        if(loggedIn != null && loggedIn){
            return ResponseEntity.ok("Logged in");
        }
        return ResponseEntity.status(401).body("Not logged in");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }
    
    
    private final QuotationRepository quotationRepository;

    public AdminController(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

	/*
	 * // Get all quotations
	 * 
	 * @GetMapping("/quotations") public ResponseEntity<List<QuotationRequest>>
	 * getAllQuotations(HttpSession session){ Boolean loggedIn = (Boolean)
	 * session.getAttribute("ADMIN_LOGGED_IN"); if(loggedIn == null || !loggedIn){
	 * return ResponseEntity.status(401).build(); } List<QuotationRequest>
	 * quotations = quotationRepository.findAll(); quotations.sort((a,b) ->
	 * b.getCreatedAt().compareTo(a.getCreatedAt())); // latest first return
	 * ResponseEntity.ok(quotations); }
	 */
    
}
