package com.society.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.society.dto.SocietyRegistrationRequest;
import com.society.service.SocietyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class SocietyRegistrationController {

    private final SocietyService societyService;

    @PostMapping("/register-society")
    public ResponseEntity<String> registerSociety(
            @Valid @RequestBody SocietyRegistrationRequest request) {

        societyService.registerSocietyWithAdmin(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Society registered successfully. Admin created.");
    }
}

