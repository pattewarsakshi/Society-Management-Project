package com.society.repository;

import com.society.entity.Facility;
import com.society.entityenum.FacilityStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {

    // filter ACTIVE facilities
    List<Facility> findByStatus(FacilityStatus status);
}
