package com.society.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.society.dto.CreateVisitorParkingDTO;
import com.society.entity.ParkingSlot;
import com.society.entity.Society;
import com.society.entityenum.ParkingStatus;
import com.society.entityenum.ParkingType;
import com.society.exception.ResourceNotFoundException;
import com.society.repository.ParkingSlotRepository;
import com.society.repository.SocietyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminVisitorParkingService {

    private final ParkingSlotRepository parkingRepo;
    private final SocietyRepository societyRepo;

    public void createVisitorParking(
            Integer societyId,
            CreateVisitorParkingDTO dto) {

        Society society = societyRepo.findById(societyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Society not found"));

        ParkingSlot slot = new ParkingSlot();
        slot.setSlotNumber(dto.getSlotNumber());
        slot.setVehicleType(dto.getVehicleType());
        slot.setParkingType(ParkingType.VISITOR);
        slot.setStatus(ParkingStatus.FREE);
        slot.setSociety(society);

        parkingRepo.save(slot);
    }
}
