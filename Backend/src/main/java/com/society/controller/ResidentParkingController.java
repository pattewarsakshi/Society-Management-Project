package com.society.controller;

import org.springframework.web.bind.annotation.*;

import com.society.dto.ParkingDetailsResponseDTO;
import com.society.service.ParkingViewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/resident/parking")
@RequiredArgsConstructor
public class ResidentParkingController {

    private final ParkingViewService parkingViewService;

    // 🔹 Resident views parking details
    @GetMapping("/{flatId}")
    public ParkingDetailsResponseDTO getParkingDetails(
            @PathVariable Integer flatId) {

        return parkingViewService.getParkingDetails(flatId);
    }
}