package com.society.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitorResponseDTO {

    private Integer visitorId;
    private String visitorName;
    private String phone;
    private String purpose;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    private boolean hasVehicle;
    private String vehicleType;
    private String vehicleNumber;

    private String visitorPhoto;

    // ✅ ONLY ADDITION
    private Long flatId;
}
