package com.society.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.society.dto.AdminParkingResponseDTO;
import com.society.entity.ParkingSlot;
import com.society.entity.User;
import com.society.entityenum.Role;
import com.society.repository.ParkingSlotRepository;
import com.society.util.LoggedInUserUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminParkingService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final LoggedInUserUtil loggedInUserUtil;

    public List<AdminParkingResponseDTO> getAllParking(HttpSession session) {

        User admin = loggedInUserUtil.getUser(session);

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Admin access only");
        }

        return parkingSlotRepository
                .findBySociety_SocietyId(admin.getSociety().getSocietyId())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<AdminParkingResponseDTO> getParkingByFlatId(
            Integer flatId,
            HttpSession session) {

        User admin = loggedInUserUtil.getUser(session);

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Admin access only");
        }

        return parkingSlotRepository
                .findByFlat_FlatId(flatId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private AdminParkingResponseDTO mapToDTO(ParkingSlot slot) {
        return new AdminParkingResponseDTO(
                slot.getParkingId(),
                slot.getSlotNumber(),
                slot.getStatus().name(),
                slot.getVehicleType().name(),
                slot.getFlat() != null ? slot.getFlat().getFlatNumber() : "-",
                slot.getFlat() != null ? slot.getFlat().getTowerName() : "-"
            );
    }
}
