package com.society.dto;

import com.society.entityenum.FacilityName;
import com.society.entityenum.FacilityStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FacilityResponseDTO {

    private Integer facilityId;
    private FacilityName facilityName;
    private Integer capacity;
    private Boolean bookingRequired;
    private FacilityStatus status;
}
