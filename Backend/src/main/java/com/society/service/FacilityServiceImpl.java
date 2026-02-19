package com.society.service;

import com.society.dto.FacilityRequestDTO;
import com.society.dto.FacilityResponseDTO;
import com.society.entity.Facility;
import com.society.entity.Society;
import com.society.entity.User;
import com.society.entityenum.FacilityStatus;
import com.society.entityenum.Role;
import com.society.repository.FacilityRepository;
import com.society.util.LoggedInUserUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;
    private final LoggedInUserUtil loggedInUserUtil;

    // ================================
    // ADD FACILITY (ADMIN ONLY)
    // ================================
    @Override
    @Transactional
    public FacilityResponseDTO addFacility(
            FacilityRequestDTO dto,
            HttpSession session) {

        User user = loggedInUserUtil.getUser(session);
        checkAdmin(user);

        Facility facility = new Facility();
        facility.setFacilityName(dto.getFacilityName());
        facility.setCapacity(dto.getCapacity());
        facility.setBookingRequired(dto.getBookingRequired());
        facility.setStatus(dto.getStatus());
        facility.setAvailableFrom(dto.getAvailableFrom());
        facility.setAvailableTo(dto.getAvailableTo());

        //  IMPORTANT: Set society
        facility.setSociety(user.getSociety());

        return map(facilityRepository.save(facility));
    }

    // ================================
    // UPDATE FACILITY (ADMIN ONLY)
    // ================================
    @Override
    @Transactional
    public FacilityResponseDTO updateFacility(
            Integer facilityId,
            FacilityRequestDTO dto,
            HttpSession session) {

        User user = loggedInUserUtil.getUser(session);
        checkAdmin(user);

        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found"));

        //  SECURITY: Prevent cross-society update
        if (!facility.getSociety().getSocietyId()
                .equals(user.getSociety().getSocietyId())) {
            throw new RuntimeException("Unauthorized access to facility");
        }

        facility.setCapacity(dto.getCapacity());
        facility.setBookingRequired(dto.getBookingRequired());
        facility.setStatus(dto.getStatus());
        facility.setAvailableFrom(dto.getAvailableFrom());
        facility.setAvailableTo(dto.getAvailableTo());

        return map(facilityRepository.save(facility));
    }

    // ================================
    // GET ALL FACILITIES (ADMIN + MEMBER)
    // ================================
    @Override
    @Transactional(readOnly = true)
    public List<FacilityResponseDTO> getAllFacilities(HttpSession session) {

        User user = loggedInUserUtil.getUser(session);
        blockGuard(user);

        return facilityRepository
                .findBySociety_SocietyId(user.getSociety().getSocietyId())
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    // ================================
    // GET ACTIVE FACILITIES ONLY
    // ================================
    @Override
    @Transactional(readOnly = true)
    public List<FacilityResponseDTO> getActiveFacilities(HttpSession session) {

        User user = loggedInUserUtil.getUser(session);
        blockGuard(user);

        return facilityRepository
                .findBySociety_SocietyIdAndStatus(
                        user.getSociety().getSocietyId(),
                        FacilityStatus.ACTIVE
                )
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    // ================================
    // HELPERS
    // ================================
    private void checkAdmin(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin allowed");
        }
    }

    private void blockGuard(User user) {
        if (user.getRole() == Role.GUARD) {
            throw new RuntimeException("Guard access denied");
        }
    }

    private FacilityResponseDTO map(Facility f) {
        return new FacilityResponseDTO(
                f.getFacilityId(),
                f.getFacilityName(),
                f.getCapacity(),
                f.getBookingRequired(),
                f.getStatus()
        );
    }
}
