package com.flypay.flypayportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flypay.flypayportal.model.AuthenticationResponse;
import com.flypay.flypayportal.model.User;
import com.flypay.flypayportal.service.impl.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;
    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(
           @Valid  @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(
         @Valid  @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
