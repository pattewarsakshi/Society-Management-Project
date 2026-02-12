package com.society.controller;

import com.society.dto.RegisterRequestDTO;
import com.society.dto.ResetPasswordRequestDTO;
import com.society.dto.ForgotPasswordRequestDTO;
import com.society.dto.LoginRequestDTO;
import com.society.dto.LoginResponseDTO;
import com.society.entity.User;
import com.society.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // SELF REGISTER (OWNER / RESIDENT)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {

        User user = userService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully with ID: " + user.getUserId());
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request,
            HttpSession session) {

        return ResponseEntity.ok(
                userService.loginUser(request, session)
        );
        
        
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 🔴 THIS IS MANDATORY
        }
        return ResponseEntity.ok("Logged out successfully");
    }
    
 // CHECK LOGGED IN USER (SESSION BASED)
    @GetMapping("/me")
    public ResponseEntity<LoginResponseDTO> getLoggedInUser(HttpSession session) {

        Integer userId = (Integer) session.getAttribute("LOGGED_USER_ID");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoginResponseDTO response = userService.getUserById(userId);

        return ResponseEntity.ok(response);
    }
    
// // AuthController.java
//    @PostMapping("/forgot-password")
//    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {
//        userService.forgotPassword(request.getEmail());
//        return ResponseEntity.ok("Reset link sent (check console in this demo)");
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDTO request) {
//        userService.resetPassword(request.getToken(), request.getNewPassword());
//        return ResponseEntity.ok("Password updated successfully");
//    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {
        // Check if email exists
        User user = userService.getUserByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        }

        // For simplified flow, we skip sending email and just let frontend proceed
        // You can optionally generate a temporary token if you want

        return ResponseEntity.ok("Email verified");
    }


    
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDTO request) {
        // request.getEmail() instead of token
        User user = userService.getUserByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Update password
        userService.updatePassword(user, request.getNewPassword());

        return ResponseEntity.ok("Password updated successfully");
    }


}
