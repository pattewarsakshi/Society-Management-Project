package com.society.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.society.entity.User;
import com.society.service.AdminUserService;
import com.society.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/guards")
@RequiredArgsConstructor
public class AdminGuardController {

    private final AdminUserService adminUserService;
    private final UserService userService;

    // ===============================
    // Get Pending Guards List
    // ===============================
    @GetMapping("/pending")
    public ResponseEntity<List<User>> getPendingGuards(HttpSession session) {

        User admin = userService.getLoggedInUser(session);
        Integer societyId = admin.getSociety().getSocietyId();

        return ResponseEntity.ok(
                adminUserService.getPendingGuards(societyId)
        );
    }

    // ===============================
    // Get Pending Guards Count
    // ===============================
    @GetMapping("/pending/count")
    public ResponseEntity<Integer> getPendingGuardCount(HttpSession session) {

        User admin = userService.getLoggedInUser(session);
        Integer societyId = admin.getSociety().getSocietyId();

        return ResponseEntity.ok(
                adminUserService.getPendingGuardCount(societyId)
        );
    }

    // ===============================
    // Approve Guard
    // ===============================
    @PutMapping("/approve/{guardId}")
    public ResponseEntity<String> approveGuard(@PathVariable Integer guardId) {

        adminUserService.approveGuard(guardId);
        return ResponseEntity.ok("Guard approved successfully");
    }

    // ===============================
    // Reject Guard
    // ===============================
    @PutMapping("/reject/{guardId}")
    public ResponseEntity<String> rejectGuard(@PathVariable Integer guardId) {

        adminUserService.rejectGuard(guardId);
        return ResponseEntity.ok("Guard rejected successfully");
    }
}

