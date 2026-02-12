package com.society.dto;

import java.time.LocalDate;

import com.society.entityenum.PaymentStatus;

import lombok.Data;

@Data
public class MaintenanceResponseDTO {
    private Integer maintenanceId;
    private Integer flatId;
    private String towerName;     // From Flat
    private String flatNumber;    // From Flat
    private Double amount;
    private LocalDate dueDate;
    private LocalDate overDueDate;
    private LocalDate paymentDate;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private String paymentProof;
    
 // 🔹 Billing breakdown
    private Double baseAmount;
    private Double parkingCharge;
    private Double totalAmount;
}