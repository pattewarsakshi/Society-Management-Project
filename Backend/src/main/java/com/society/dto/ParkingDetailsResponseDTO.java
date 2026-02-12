package com.society.dto;

import java.util.List;

import lombok.Data;

@Data
public class ParkingDetailsResponseDTO {
    private Integer flatId;
    private List<ParkingSlotInfoDTO> parkingSlots;

    @Data
    public static class ParkingSlotInfoDTO {
    	private String vehicleType;
    	private String slotNumber;
        private String status;    
        
    }
}