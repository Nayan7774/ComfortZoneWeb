package com.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// Removed @Entity and @Table annotations
public class ContactForm {

    // Removed @Id and @GeneratedValue because we aren't saving to a DB
    private Long id;  

    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(regexp = "^(?:\\+91|0)?[6-9]\\d{9}$", message = "Invalid phone number")
    private String phone;
    
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    private String message;

    public ContactForm() {}

    public ContactForm(String name, String phone, String email, String message) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.message = message;
    }

    // Getters and Setters remain the same
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}