package com.society.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.society.dto.GuardRegisterDTO;
import com.society.entity.Society;
import com.society.entity.User;
import com.society.entityenum.Role;
import com.society.entityenum.AccountStatus; // 🔥 ADDED
import com.society.exception.ResourceNotFoundException;
import com.society.repository.SocietyRepository;
import com.society.repository.UserRepository;
import com.society.util.LoggedInUserUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GuardServiceImpl implements GuardService {

    private final UserRepository userRepo;
    private final SocietyRepository societyRepo;
    private final PasswordEncoder passwordEncoder;
    private final LoggedInUserUtil loggedInUserUtil;

    @Override
    public User registerGuard(GuardRegisterDTO dto, HttpSession session) {

        User admin = loggedInUserUtil.getUser(session);
        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can register guards");
        }

        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Society society = societyRepo.findById(dto.getSocietyId())
                .orElseThrow(() -> new ResourceNotFoundException("Society not found"));

        User guard = new User();
        guard.setFirstName(dto.getFirstName());
        guard.setLastName(dto.getLastName());
        guard.setEmail(dto.getEmail());
        guard.setPhone(dto.getPhone());
        guard.setPassword(passwordEncoder.encode(dto.getPassword()));
        guard.setRole(Role.GUARD);
        guard.setSociety(society);

        //  ADDED: Guard needs admin approval before becoming active
        guard.setAccountStatus(AccountStatus.PENDING);

        return userRepo.save(guard);
    }
}
