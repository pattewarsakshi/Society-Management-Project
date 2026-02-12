package com.society.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.society.entity.Maintenance;
import com.society.entityenum.PaymentStatus;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer>{
	List<Maintenance> findByFlat_FlatId(Integer flatId);

    List<Maintenance> findByPaymentStatus(PaymentStatus status);
    
    // Fetch the latest pending maintenance for a flat
    @Query("SELECT m FROM Maintenance m " +
           "WHERE m.flat.flatId = :flatId AND m.paymentStatus = :status " +
           "ORDER BY m.dueDate ASC")
    Optional<Maintenance> findFirstByFlatIdAndPendingStatus(
            @Param("flatId") Integer flatId,
            @Param("status") PaymentStatus status);
}