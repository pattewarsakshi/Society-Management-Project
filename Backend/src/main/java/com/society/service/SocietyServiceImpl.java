package com.society.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.society.dto.SocietyRegistrationRequest;
import com.society.entity.Society;
import com.society.entity.User;
import com.society.entityenum.Role;
import com.society.repository.SocietyRepository;
import com.society.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocietyServiceImpl implements SocietyService {

    private final SocietyRepository societyRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

    @Override
    public Society createSociety(Society society) {

        // Prevent duplicate society
        if (societyRepository.existsBySocietyName(society.getSocietyName())) {
            throw new RuntimeException("Society already exists");
        }

        // Save society
        return societyRepository.save(society);
    }

    @Override
    public Society getSocietyById(Integer societyId) {
        return societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));
    }

	@Override
	public List<Society> getAllSocieties() {
	    return societyRepository.findAll();
	}

	@Override
    @Transactional
	public void registerSocietyWithAdmin(SocietyRegistrationRequest req) {
        if (societyRepository.existsBySocietyName(req.getSocietyName())) {
            throw new RuntimeException("Society already exists");
        }

        if (userRepository.existsByEmail(req.getAdminEmail())) {
            throw new RuntimeException("Admin email already exists");
        }

        Society society = new Society();
        society.setSocietyName(req.getSocietyName());
        society.setAddress(req.getAddress());
        society.setCity(req.getCity());
        society.setPincode(req.getPincode());

        societyRepository.save(society);

        User admin = new User();
        admin.setFirstName("Super");
        admin.setLastName("Admin");
        admin.setEmail(req.getAdminEmail());
        admin.setPassword(passwordEncoder.encode(req.getAdminPassword()));
        admin.setRole(Role.ADMIN);
        admin.setSociety(society);

        userRepository.save(admin);
		
	}
}

