package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MailService;

@RestController
@RequestMapping("/oauth2callback")
public class OAuth2CallbackController {

    @GetMapping
    public String handleOAuthCallback(@RequestParam("code") String code) {
        // 'code' is the authorization code from Google
        // Exchange this code for access token
        try {
            String accessToken = MailService.exchangeCodeForToken(code);
            return "Gmail API authorization successful!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during OAuth callback: " + e.getMessage();
        }
    }
}
