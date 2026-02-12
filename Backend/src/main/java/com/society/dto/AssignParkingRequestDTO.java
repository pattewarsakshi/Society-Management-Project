package com.society.dto;

import com.society.entityenum.VehicleType;

import lombok.Data;

@Data
public class AssignParkingRequestDTO {
    private Integer flatId;
    private String slotNumber;
    private VehicleType vehicleType; // BIKE / CAR / NONE
}