package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.entity.Complaint;
import com.society.entity.User;
import com.society.entityenum.ComplaintStatus;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

    // Resident → view own complaints
    List<Complaint> findByUserUserId(Integer userId);

    // Admin → search by society + tower + flat
    List<Complaint>
    findByFlatSocietySocietyIdAndFlatTowerNameAndFlatFlatNumber(
            Integer societyId,
            String towerName,
            String flatNumber
    );
    
    
    // Get all complaints in a society
    List<Complaint> findByFlatSocietySocietyId(Integer societyId);
    
    long countByStatusIn(Iterable<ComplaintStatus> statuses);
    
    int countByUserAndStatus(User user, ComplaintStatus status);


}

