package com.society.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.society.dto.CreateVisitorParkingDTO;
import com.society.service.AdminVisitorParkingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/visitor-parking")
@RequiredArgsConstructor
@CrossOrigin
public class AdminVisitorParkingController {

    private final AdminVisitorParkingService service;

    // CREATE VISITOR PARKING SLOT
    @PostMapping("/society/{societyId}")
    public ResponseEntity<?> createVisitorParking(
            @PathVariable Integer societyId,
            @RequestBody CreateVisitorParkingDTO dto) {

        service.createVisitorParking(societyId, dto);
        return ResponseEntity.ok("Visitor parking slot created successfully");
    }
}
