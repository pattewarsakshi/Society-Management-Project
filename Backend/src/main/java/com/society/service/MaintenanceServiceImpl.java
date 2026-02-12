package com.society.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.society.dto.MaintenanceRequestDTO;
import com.society.dto.MaintenanceResponseDTO;
import com.society.entity.Flat;
import com.society.entity.Maintenance;
import com.society.entity.User;
import com.society.entityenum.PaymentStatus;
import com.society.repository.FlatRepository;
import com.society.repository.MaintenanceRepository;
import com.society.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final FlatRepository flatRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    // ================= ADMIN =================
    @Override
    public MaintenanceResponseDTO createMaintenance(
            MaintenanceRequestDTO dto,
            HttpSession session) {

        User admin = getLoggedInUser(session);

        Flat flat = flatRepository
                .findBySociety_SocietyIdAndTowerNameAndFlatNumber(
                        admin.getSociety().getSocietyId(),
                        dto.getTowerName(),
                        dto.getFlatNumber()
                )
                .orElseThrow(() -> new RuntimeException("Flat not found"));

        Maintenance m = new Maintenance();
        m.setFlat(flat);
        m.setAmount(dto.getAmount());
        m.setDueDate(dto.getDueDate());
        m.setOverDueDate(dto.getOverDueDate());
        m.setPaymentStatus(PaymentStatus.PENDING);

        return mapToDTO(maintenanceRepository.save(m));
    }

    @Override
    public List<MaintenanceResponseDTO> getAllMaintenance(HttpSession session) {

        User admin = getLoggedInUser(session);

        return maintenanceRepository.findAll()
                .stream()
                .filter(m -> m.getFlat().getSociety().getSocietyId()
                        .equals(admin.getSociety().getSocietyId()))
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public MaintenanceResponseDTO approvePayment(Integer id) {

        Maintenance m = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));

        m.setPaymentStatus(PaymentStatus.PAID);
        m.setPaymentDate(LocalDate.now());

        return mapToDTO(maintenanceRepository.save(m));
    }

    @Override
    public MaintenanceResponseDTO rejectPayment(Integer id) {

        Maintenance m = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));

        m.setPaymentStatus(PaymentStatus.REJECTED);

        return mapToDTO(maintenanceRepository.save(m));
    }

    @Override
    public MaintenanceResponseDTO updateMaintenance(
            Integer id,
            MaintenanceRequestDTO dto,
            HttpSession session) {

        User admin = getLoggedInUser(session);

        Maintenance m = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));

        Flat flat = flatRepository
                .findBySociety_SocietyIdAndTowerNameAndFlatNumber(
                        admin.getSociety().getSocietyId(),
                        dto.getTowerName(),
                        dto.getFlatNumber()
                )
                .orElseThrow(() -> new RuntimeException("Flat not found"));

        m.setFlat(flat);
        m.setAmount(dto.getAmount());
        m.setDueDate(dto.getDueDate());
        m.setOverDueDate(dto.getOverDueDate());

        return mapToDTO(maintenanceRepository.save(m));
    }

    @Override
    public void deleteMaintenance(Integer id) {

        if (!maintenanceRepository.existsById(id)) {
            throw new RuntimeException("Maintenance not found");
        }

        maintenanceRepository.deleteById(id);
    }

    // ================= RESIDENT =================
    @Override
    public List<MaintenanceResponseDTO> getMaintenanceByFlat(Integer flatId) {

        return maintenanceRepository.findByFlat_FlatId(flatId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void uploadPaymentProof(
            Integer id,
            String transactionId,
            MultipartFile file) {

        Maintenance m = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        try {
            Path uploadPath = Paths.get("uploads");
            Files.createDirectories(uploadPath);
            Files.copy(file.getInputStream(), uploadPath.resolve(fileName));
        } catch (Exception e) {
            throw new RuntimeException("File upload failed");
        }

        m.setTransactionId(transactionId);
        m.setPaymentProof(fileName);
        m.setPaymentStatus(PaymentStatus.APPROVAL_PENDING);

        maintenanceRepository.save(m);
    }

    // ================= MAPPER =================
    private MaintenanceResponseDTO mapToDTO(Maintenance m) {

        MaintenanceResponseDTO dto =
                modelMapper.map(m, MaintenanceResponseDTO.class);

        dto.setFlatId(m.getFlat().getFlatId());
        dto.setTowerName(m.getFlat().getTowerName());
        dto.setFlatNumber(m.getFlat().getFlatNumber());

        return dto;
    }

    // ================= SESSION =================
    private User getLoggedInUser(HttpSession session) {

        Object obj = session.getAttribute("LOGGED_USER_ID");

        if (obj == null) {
            throw new RuntimeException("User not logged in");
        }

        Integer userId = (Integer) obj;

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));    
    }
    
    // Get latest pending maintenance for dashboard
    public MaintenanceResponseDTO getPendingMaintenanceForFlat(Integer flatId) {
        return maintenanceRepository
                .findFirstByFlatIdAndPendingStatus(flatId, PaymentStatus.PENDING)
                .map(this::mapToDTO)
                .orElse(null);
    }

    private MaintenanceResponseDTO convertToDTO(Maintenance m) {
        MaintenanceResponseDTO dto = new MaintenanceResponseDTO();
        dto.setMaintenanceId(m.getMaintenanceId());
        dto.setAmount(m.getAmount());
        dto.setDueDate(m.getDueDate());
        dto.setPaymentStatus(m.getPaymentStatus());
        dto.setTransactionId(m.getTransactionId());
        dto.setPaymentProof(m.getPaymentProof());
        return dto;
    }
}
