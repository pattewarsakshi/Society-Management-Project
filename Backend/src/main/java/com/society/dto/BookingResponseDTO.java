package com.society.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class BookingResponseDTO {

    private Integer bookingId;

    // Facility info
    private Integer facilityId;
    private String facilityName;

    // Booking info
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;

    // Status
    private String status;

    // User info (for admin view)
    private Integer userId;
    private String bookedBy;

    // Optional
    private String purpose;
}
