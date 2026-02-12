package com.society.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitorEntryDTO {

    private String visitorName;
    private String phone;
    private String purpose;

    private boolean hasVehicle;
    private String vehicleType;
    private String vehicleNumber;

    
    private Long flatId;
}
