package com.society.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MaintenanceRequestDTO {

    // Admin-friendly inputs
    private String towerName;
    private String flatNumber;

    private Double amount;
    private LocalDate dueDate;
    private LocalDate overDueDate;
    
    
}