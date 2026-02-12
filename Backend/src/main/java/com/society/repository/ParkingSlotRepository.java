package com.society.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.entity.ParkingSlot;
import com.society.entityenum.ParkingStatus;
import com.society.entityenum.ParkingType;
import com.society.entityenum.VehicleType;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> {

   
    List<ParkingSlot> findBySociety_SocietyId(Integer societyId);

    // used in AdminParkingService
    List<ParkingSlot> findByFlat_FlatId(Integer flatId);

    // used in VisitorService (auto-assign)
    Optional<ParkingSlot>
        findFirstBySociety_SocietyIdAndParkingTypeAndVehicleTypeAndStatus(
            Integer societyId,
            ParkingType parkingType,
            VehicleType vehicleType,
            ParkingStatus status
        );
}
