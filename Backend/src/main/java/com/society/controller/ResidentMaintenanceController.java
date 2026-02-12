package com.society.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.society.dto.MaintenanceResponseDTO;
import com.society.service.MaintenanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/resident/maintenance")
@RequiredArgsConstructor
public class ResidentMaintenanceController {

    private final MaintenanceService maintenanceService;

    @GetMapping("/flat/{flatId}")
    public ResponseEntity<List<MaintenanceResponseDTO>> getByFlat(
            @PathVariable Integer flatId) {

        return ResponseEntity.ok(
                maintenanceService.getMaintenanceByFlat(flatId)
        );
    }

    @PostMapping("/{id}/upload-proof")
    public ResponseEntity<String> uploadProof(
            @PathVariable Integer id,
            @RequestParam(required = false) String transactionId,
            @RequestParam MultipartFile file) {

        maintenanceService.uploadPaymentProof(id, transactionId, file);
        return ResponseEntity.ok(
                "Payment proof uploaded. Waiting for admin approval."
        );
    }
    
    @GetMapping("/flat/{flatId}/pending")
    public ResponseEntity<MaintenanceResponseDTO> getPendingMaintenance(
            @PathVariable Integer flatId) {
        MaintenanceResponseDTO pending = maintenanceService.getPendingMaintenanceForFlat(flatId);
        return ResponseEntity.ok(pending);
    }

}
