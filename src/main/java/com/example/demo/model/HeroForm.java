package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// Removed @Entity and @Table
public class HeroForm {

    // Removed @Id and @GeneratedValue
    private Long id; 
    
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Contact is required")
    @Pattern(regexp = "^(?:\\+91|0)?[6-9]\\d{9}$", message = "Invalid phone number")
    private String contact;

    public HeroForm() {}

    public HeroForm(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    @Override
    public String toString() {
        return "HeroForm [id=" + id + ", name=" + name + ", contact=" + contact + "]";
    }
}