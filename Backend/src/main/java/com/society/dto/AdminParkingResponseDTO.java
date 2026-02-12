package com.society.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminParkingResponseDTO {

    private Integer parkingId;
    private String slotNumber;
    private String status;
    private String vehicleType;
    private String flatNumber;  
    private String towerName;
}