

package com.society.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.society.dto.AdminParkingResponseDTO;
import com.society.service.AdminParkingService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/parking")
@RequiredArgsConstructor
public class AdminParkingController {

    private final AdminParkingService adminParkingService;

    //  ADMIN → View all parking slots in admin's society
    @GetMapping("/all")
    public List<AdminParkingResponseDTO> getAllParking(HttpSession session) {
        return adminParkingService.getAllParking(session);
    }

    // ADMIN → View ALL parking slots of a specific flat
    @GetMapping("/{flatId}")
    public List<AdminParkingResponseDTO> getParkingByFlatId(
            @PathVariable Integer flatId,
            HttpSession session) {

        return adminParkingService.getParkingByFlatId(flatId, session);
    }
}

