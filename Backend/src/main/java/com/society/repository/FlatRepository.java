package com.society.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.society.entity.Complaint;
import com.society.entity.Flat;
import com.society.entityenum.FlatStatus;


public interface FlatRepository extends JpaRepository<Flat, Integer> {

    Optional<Flat> findBySociety_SocietyIdAndTowerNameAndFlatNumber(
            Integer societyId,
            String towerName,
            String flatNumber
    );
    
    List<Flat> findBySociety_SocietyIdAndStatus(Integer societyId, FlatStatus status);


    @Query("""
        SELECT f FROM Flat f
        LEFT JOIN FETCH f.parkingSlots
        WHERE f.flatId = :flatId
    """)
    Optional<Flat> findFlatWithParking(@Param("flatId") Integer flatId);
    
    long countBySociety_SocietyId(Integer societyId);

    long countBySociety_SocietyIdAndStatus(
    		Integer societyId,
            FlatStatus status
    );

    List<Flat> findBySociety_SocietyId(Integer societyId);

}
