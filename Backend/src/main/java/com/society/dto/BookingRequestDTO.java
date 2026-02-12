package com.society.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingRequestDTO {

    // Facility to be booked
    private Integer facilityId;

    // Booking date
    private LocalDate bookingDate;

    // Time slot
    private LocalTime startTime;
    private LocalTime endTime;

    // Optional purpose (meeting, gym, party, etc.)
    private String purpose;
}
