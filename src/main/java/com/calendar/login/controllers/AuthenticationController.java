package com.calendar.login.controllers;

import com.calendar.login.configuration.AuthenticationResponse;
import com.calendar.login.dto.LoginDTO;
import com.calendar.login.dto.RegistrationDTO;
import com.calendar.login.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody RegistrationDTO request
    ) {
        return ResponseEntity.ok(service.register(request));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (
            @RequestBody LoginDTO request
    ) {
        return ResponseEntity.ok(service.login(request));
    }
}
