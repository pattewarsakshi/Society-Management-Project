package com.society.dto;

import com.society.entityenum.VehicleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVisitorParkingDTO {

    private String slotNumber;        // e.g. V-01
    private VehicleType vehicleType;  // TWO_WHEELER / FOUR_WHEELER
}
