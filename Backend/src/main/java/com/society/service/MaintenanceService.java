package com.society.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.society.dto.MaintenanceRequestDTO;
import com.society.dto.MaintenanceResponseDTO;

import jakarta.servlet.http.HttpSession;

public interface MaintenanceService {

    MaintenanceResponseDTO createMaintenance(
            MaintenanceRequestDTO dto,
            HttpSession session
    );

    List<MaintenanceResponseDTO> getAllMaintenance(HttpSession session);

    List<MaintenanceResponseDTO> getMaintenanceByFlat(Integer flatId);

    MaintenanceResponseDTO approvePayment(Integer id);

    MaintenanceResponseDTO rejectPayment(Integer id);

    MaintenanceResponseDTO updateMaintenance(
            Integer id,
            MaintenanceRequestDTO dto,
            HttpSession session
    );

    void deleteMaintenance(Integer id);

    void uploadPaymentProof(
            Integer id,
            String transactionId,
            MultipartFile file
    );
    
    public MaintenanceResponseDTO getPendingMaintenanceForFlat(Integer flatId);

}
