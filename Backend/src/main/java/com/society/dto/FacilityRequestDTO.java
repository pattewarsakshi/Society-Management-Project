package com.society.dto;

import java.time.LocalDate;

import com.society.entityenum.FacilityName;
import com.society.entityenum.FacilityStatus;

import lombok.Data;

@Data
public class FacilityRequestDTO {
    private FacilityName facilityName;
    private Integer capacity;
    private Boolean bookingRequired;
    private FacilityStatus status;
    private LocalDate availableFrom;
    private LocalDate availableTo;

}
