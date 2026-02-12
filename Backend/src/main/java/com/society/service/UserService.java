package com.society.service;

import com.society.dto.LoginRequestDTO;
import com.society.dto.LoginResponseDTO;
import com.society.dto.RegisterRequestDTO;
import com.society.entity.User;

import jakarta.servlet.http.HttpSession;

public interface UserService {

    // Register user (Admin or Self)
    User registerUser(RegisterRequestDTO dto);

    // Login user
    LoginResponseDTO loginUser(LoginRequestDTO request, HttpSession session);
    
    // To get User by UserId
    LoginResponseDTO getUserById(Integer userId);
    
    User getLoggedInUser(HttpSession session);

	void forgotPassword(String email);

	void resetPassword(String token, String newPassword);

	User getUserByEmail(String email);

	void updatePassword(User user, String newPassword); 

}
