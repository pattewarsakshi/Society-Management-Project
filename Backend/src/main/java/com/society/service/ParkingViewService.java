package com.society.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.society.dto.ParkingDetailsResponseDTO;
import com.society.entity.Flat;
import com.society.entity.ParkingSlot;
import com.society.entityenum.ParkingType;
import com.society.repository.FlatRepository;
import com.society.repository.ParkingSlotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkingViewService {

    private final ParkingSlotRepository parkingSlotRepository;

    @Transactional(readOnly = true)
    public ParkingDetailsResponseDTO getParkingDetails(Integer flatId) {

        ParkingDetailsResponseDTO dto = new ParkingDetailsResponseDTO();
        dto.setFlatId(flatId);

        // 🔹 Fetch parking slots directly
        List<ParkingSlot> slots = parkingSlotRepository.findByFlat_FlatId(flatId);

        if (slots == null || slots.isEmpty()) {
            dto.setParkingSlots(List.of());
            return dto;
        }

        List<ParkingDetailsResponseDTO.ParkingSlotInfoDTO> responseSlots =
                slots.stream()
                        // 🔹 Only RESIDENT parking
                        .filter(s -> s.getParkingType() == ParkingType.RESIDENT)
                        .map(slot -> {
                            ParkingDetailsResponseDTO.ParkingSlotInfoDTO s =
                                    new ParkingDetailsResponseDTO.ParkingSlotInfoDTO();

                            s.setSlotNumber(slot.getSlotNumber());
                            s.setStatus(slot.getStatus().name());          // FREE / OCCUPIED
                            s.setVehicleType(slot.getVehicleType().name()); // FOUR_WHEELER

                            return s;
                        })
                        .toList();

        dto.setParkingSlots(responseSlots);
        return dto;
    }
}
