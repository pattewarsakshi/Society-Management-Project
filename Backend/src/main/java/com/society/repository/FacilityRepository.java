package com.society.repository;

import com.society.entity.Facility;
import com.society.entityenum.FacilityStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {

    // Get all facilities of a society
    List<Facility> findBySociety_SocietyId(Integer societyId);

    // Get active facilities of a society
    List<Facility> findBySociety_SocietyIdAndStatus(
            Integer societyId,
            FacilityStatus status
    );
}
