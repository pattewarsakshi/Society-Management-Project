package com.society.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.society.dto.GuardRegisterDTO;
import com.society.service.GuardService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guards")
@RequiredArgsConstructor
@CrossOrigin
public class GuardController {

    private final GuardService guardService;

    // ✅ REGISTER GUARD (ADMIN ONLY)
    @PostMapping("/register")
    public ResponseEntity<?> registerGuard(
            @RequestBody GuardRegisterDTO dto,
            HttpSession session) {

        return ResponseEntity.ok(
                guardService.registerGuard(dto, session)
        );
    }

    
}
