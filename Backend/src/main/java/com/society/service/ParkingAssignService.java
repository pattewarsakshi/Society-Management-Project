package com.society.service;

import org.springframework.stereotype.Service;

import com.society.dto.AssignParkingRequestDTO;
import com.society.entity.Flat;
import com.society.entity.ParkingSlot;
import com.society.entity.User;
import com.society.entityenum.ParkingStatus;
import com.society.entityenum.ParkingType;
import com.society.entityenum.Role;
import com.society.repository.FlatRepository;
import com.society.repository.ParkingSlotRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkingAssignService {

    private final FlatRepository flatRepository;
    private final ParkingSlotRepository parkingSlotRepository;

    public void assignParking(
            AssignParkingRequestDTO dto,
            HttpSession session) {

        User admin = (User) session.getAttribute("LOGGED_IN_USER");

        if (admin == null || admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin can assign parking");
        }

        Flat flat = flatRepository.findById(dto.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat not found"));

        ParkingSlot slot = new ParkingSlot();
        slot.setSlotNumber(dto.getSlotNumber());
        slot.setVehicleType(dto.getVehicleType());
        slot.setParkingType(ParkingType.RESIDENT);
        slot.setStatus(ParkingStatus.FREE);
        slot.setFlat(flat);
        slot.setSociety(flat.getSociety());

        parkingSlotRepository.save(slot);
    }
}
