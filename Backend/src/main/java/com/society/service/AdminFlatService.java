package com.society.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.society.dto.FlatDashboardCountDTO;
import com.society.dto.FlatDetailsDTO;
import com.society.entityenum.FlatStatus;
import com.society.repository.FlatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminFlatService {

    private final FlatRepository flatRepository;

    public FlatDashboardCountDTO getFlatCounts(Integer societyId) {

        long total = flatRepository.countBySociety_SocietyId(societyId);
        long occupied = flatRepository.countBySociety_SocietyIdAndStatus(
                societyId,
                FlatStatus.OCCUPIED
        );
        long vacant = flatRepository.countBySociety_SocietyIdAndStatus(
                societyId,
                FlatStatus.VACANT
        );

        return new FlatDashboardCountDTO(
                total,
                occupied,
                vacant
        );
    }

    public List<FlatDetailsDTO> getAllFlats(Integer societyId) {

        return flatRepository
                .findBySociety_SocietyId(societyId)
                .stream()
                .map(flat -> new FlatDetailsDTO(
                        flat.getFlatId(),
                        flat.getSociety().getSocietyName(),
                        flat.getTowerName(),
                        flat.getFlatNumber(),
                        flat.getAreaSqft(),
                        flat.getStatus().name()
                ))
                .toList();
    }
}

