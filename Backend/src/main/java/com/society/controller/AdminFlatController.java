package com.society.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.society.dto.FlatDashboardCountDTO;
import com.society.dto.FlatDetailsDTO;
import com.society.service.AdminFlatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/flats")
@RequiredArgsConstructor
public class AdminFlatController {

    private final AdminFlatService adminFlatService;

    // dashboard bucket
    @GetMapping("/count")
    public FlatDashboardCountDTO getFlatCounts(
            @RequestParam Integer societyId
    ) {
        return adminFlatService.getFlatCounts(societyId);
    }

    // manage flats page
    @GetMapping
    public List<FlatDetailsDTO> getAllFlats(
            @RequestParam Integer societyId
    ) {
        return adminFlatService.getAllFlats(societyId);
    }
    
}
