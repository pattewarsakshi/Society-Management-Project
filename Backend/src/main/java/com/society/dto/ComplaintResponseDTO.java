package com.society.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ComplaintResponseDTO {
    private Integer complaintId;
    private String complaintType;
    private String description;
    private String status;
    private LocalDate dateFiled;
    private LocalDate dateResolved;

    private Integer flatId;
    private Integer userId;
}
