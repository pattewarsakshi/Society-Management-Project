package com.society.controller;

import com.society.dto.FacilityRequestDTO;
import com.society.dto.FacilityResponseDTO;
import com.society.service.FacilityService;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facilities")
@RequiredArgsConstructor
@CrossOrigin
public class FacilityController {

    private final FacilityService facilityService;

    // ADMIN
    @PostMapping
    public ResponseEntity<FacilityResponseDTO> addFacility(
            @RequestBody FacilityRequestDTO dto,
            HttpSession session) {

        return ResponseEntity.ok(
                facilityService.addFacility(dto, session));
    }

    // ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<FacilityResponseDTO> updateFacility(
            @PathVariable Integer id,
            @RequestBody FacilityRequestDTO dto,
            HttpSession session) {

        return ResponseEntity.ok(
                facilityService.updateFacility(id, dto, session));
    }

    // ADMIN + MEMBER
    @GetMapping
    public ResponseEntity<List<FacilityResponseDTO>> getAll(
            HttpSession session) {

        return ResponseEntity.ok(
                facilityService.getAllFacilities(session));
    }

    //  ONLY ACTIVE FACILITIES
    @GetMapping("/active")
    public ResponseEntity<List<FacilityResponseDTO>> getActive(
            HttpSession session) {

        return ResponseEntity.ok(
                facilityService.getActiveFacilities(session));
    }
}
