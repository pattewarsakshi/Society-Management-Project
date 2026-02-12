package com.society.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.society.dto.MaintenanceRequestDTO;
import com.society.dto.MaintenanceResponseDTO;
import com.society.service.MaintenanceService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/maintenance")
@RequiredArgsConstructor
public class AdminMaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<MaintenanceResponseDTO> create(
            @RequestBody MaintenanceRequestDTO dto,
            HttpSession session) {

        return ResponseEntity.ok(
                maintenanceService.createMaintenance(dto, session)
        );
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceResponseDTO>> getAll(
            HttpSession session) {

        return ResponseEntity.ok(
                maintenanceService.getAllMaintenance(session)
        );
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<MaintenanceResponseDTO> approve(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                maintenanceService.approvePayment(id)
        );
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<MaintenanceResponseDTO> reject(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                maintenanceService.rejectPayment(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDTO> update(
            @PathVariable Integer id,
            @RequestBody MaintenanceRequestDTO dto,
            HttpSession session) {

        return ResponseEntity.ok(
                maintenanceService.updateMaintenance(id, dto, session)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}
