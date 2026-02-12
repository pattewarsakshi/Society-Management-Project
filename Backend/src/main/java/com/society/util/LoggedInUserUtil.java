package com.society.util;

import com.society.entity.User;
import com.society.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggedInUserUtil {

    private final UserRepository userRepository;

    public User getUser(HttpSession session) {

        Integer userId = (Integer) session.getAttribute("LOGGED_USER_ID");

        if (userId == null) {
            throw new RuntimeException("User not logged in");
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
