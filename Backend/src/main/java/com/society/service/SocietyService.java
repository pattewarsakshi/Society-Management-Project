package com.society.service;

import java.util.List;

import com.society.dto.SocietyRegistrationRequest;
import com.society.entity.Society;

public interface SocietyService {

    Society createSociety(Society society);

    Society getSocietyById(Integer societyId);

	List<Society> getAllSocieties();
	
    // ✅ NEW (public registration)
    void registerSocietyWithAdmin(SocietyRegistrationRequest request);
}
