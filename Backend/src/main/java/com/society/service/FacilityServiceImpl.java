package com.society.service;

import com.society.dto.FacilityRequestDTO;
import com.society.dto.FacilityResponseDTO;
import com.society.entity.Facility;
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

    // ADMIN only
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

        return map(facilityRepository.save(facility));
    }

    // ADMIN only
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

        facility.setCapacity(dto.getCapacity());
        facility.setBookingRequired(dto.getBookingRequired());
        facility.setStatus(dto.getStatus());

        return map(facilityRepository.save(facility));
    }

    // ADMIN + MEMBER
    @Override
    @Transactional(readOnly = true)
    public List<FacilityResponseDTO> getAllFacilities(HttpSession session) {

        User user = loggedInUserUtil.getUser(session);
        blockGuard(user);

        return facilityRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    // ONLY ACTIVE FACILITIES
    @Override
    @Transactional(readOnly = true)
    public List<FacilityResponseDTO> getActiveFacilities(HttpSession session) {

        User user = loggedInUserUtil.getUser(session);
        blockGuard(user);

        return facilityRepository.findByStatus(FacilityStatus.ACTIVE)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    // helpers
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
