package com.society.service;

import com.society.dto.FacilityRequestDTO;
import com.society.dto.FacilityResponseDTO;

import jakarta.servlet.http.HttpSession;
import java.util.List;

public interface FacilityService {

    FacilityResponseDTO addFacility(FacilityRequestDTO dto, HttpSession session);

    FacilityResponseDTO updateFacility(
            Integer facilityId,
            FacilityRequestDTO dto,
            HttpSession session);

    List<FacilityResponseDTO> getAllFacilities(HttpSession session);

    // 🔹 ACTIVE filter
    List<FacilityResponseDTO> getActiveFacilities(HttpSession session);
}
