package com.society.service;

import com.society.dto.ProfileResponseDTO;
import com.society.dto.ProfileUpdateDTO;

import jakarta.servlet.http.HttpSession;

public interface ProfileService {

    ProfileResponseDTO getProfile(HttpSession session);

    ProfileResponseDTO updateProfile(ProfileUpdateDTO dto, HttpSession session);
}
