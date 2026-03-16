package com.example.demo.controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class GetRefreshToken {

    public static void main(String[] args) throws Exception {

        String CLIENT_ID = "501176670310-bubn90n4q3qer70u8akd3t9d71gmh9cd.apps.googleusercontent.com";
        String CLIENT_SECRET = "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";
        // USE ONLY ONE
        String REDIRECT_URI = "http://localhost:8080/oauth2callback";

        String scope = "https://www.googleapis.com/auth/gmail.send";

        String authorizationUrl = new GoogleAuthorizationCodeRequestUrl(
                CLIENT_ID,
                REDIRECT_URI,
                Collections.singleton(scope))
                .setAccessType("offline")
                .set("prompt", "consent")
                .build();

        System.out.println("Open this URL in browser:");
        System.out.println(authorizationUrl);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Paste the authorization code:");
        String code = br.readLine();

        GoogleTokenResponse tokenResponse =
                new GoogleAuthorizationCodeTokenRequest(
                        new NetHttpTransport(),
                        JacksonFactory.getDefaultInstance(),
                        "https://oauth2.googleapis.com/token",
                        CLIENT_ID,
                        CLIENT_SECRET,
                        code,
                        REDIRECT_URI)
                        .execute();
        
       

        System.out.println("Refresh Token: " + tokenResponse.getRefreshToken());
    }
}