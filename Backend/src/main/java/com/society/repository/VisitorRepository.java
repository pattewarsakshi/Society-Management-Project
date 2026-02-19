package com.society.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.entity.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Integer> {

    Optional<Visitor> findByVisitorIdAndSociety_SocietyId(
            Integer visitorId,
            Integer societyId
    );

    List<Visitor> findBySociety_SocietyId(Integer societyId);

    List<Visitor> findBySociety_SocietyIdAndExitTimeIsNull(Integer societyId);

    List<Visitor> findBySociety_SocietyIdAndEntryTimeBetween(
            Integer societyId,
            LocalDateTime start,
            LocalDateTime end
    );

    Optional<Visitor> findByPhoneAndExitTimeIsNullAndSociety_SocietyId(
            String phone,
            Integer societyId
    );
}
