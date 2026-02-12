package com.society.service;

import com.society.dto.ProfileResponseDTO;
import com.society.dto.ProfileUpdateDTO;
import com.society.entity.User;
import com.society.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    
    // VIEW PROFILE
    
    @Override
    @Transactional(readOnly = true)
    public ProfileResponseDTO getProfile(HttpSession session) {

        Integer userId = (Integer) session.getAttribute("LOGGED_USER_ID");

        if (userId == null) {
            throw new RuntimeException("UNAUTHORIZED");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToDto(user);
    }

    
    // UPDATE PROFILE
    
    @Override
    @Transactional
    public ProfileResponseDTO updateProfile(
            ProfileUpdateDTO dto,
            HttpSession session) {

        Integer userId = (Integer) session.getAttribute("LOGGED_USER_ID");

        if (userId == null) {
            throw new RuntimeException("UNAUTHORIZED");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // update only editable fields
        user.setFirstName(dto.getFirstName());
        user.setMiddleName(dto.getMiddleName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());

        User updatedUser = userRepository.save(user);

        return mapToDto(updatedUser);
    }

  
    // MAPPER
   
    private ProfileResponseDTO mapToDto(User user) {

        return new ProfileResponseDTO(
                user.getUserId(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().name(),
                user.getSociety() != null ? user.getSociety().getSocietyId() : null,
                user.getFlat() != null ? user.getFlat().getFlatId() : null
        );
    }
}
