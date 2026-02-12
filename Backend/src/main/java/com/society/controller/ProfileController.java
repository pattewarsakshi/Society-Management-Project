package com.society.controller;

import com.society.dto.ProfileResponseDTO;
import com.society.dto.ProfileUpdateDTO;
import com.society.service.ProfileService;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin
public class ProfileController {

    private final ProfileService profileService;

    // 🔹 VIEW PROFILE
    @GetMapping
    public ResponseEntity<ProfileResponseDTO> getProfile(HttpSession session) {
        return ResponseEntity.ok(profileService.getProfile(session));
    }

    // 🔹 UPDATE PROFILE
    @PutMapping
    public ResponseEntity<ProfileResponseDTO> updateProfile(
            @RequestBody ProfileUpdateDTO dto,
            HttpSession session) {

        return ResponseEntity.ok(profileService.updateProfile(dto, session));
    }
}
