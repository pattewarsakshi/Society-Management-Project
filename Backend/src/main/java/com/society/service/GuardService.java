package com.society.service;

import com.society.dto.GuardRegisterDTO;
import com.society.entity.User;

import jakarta.servlet.http.HttpSession;

public interface GuardService {

    User registerGuard(GuardRegisterDTO dto, HttpSession session);
}
