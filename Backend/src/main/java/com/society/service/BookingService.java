package com.society.service;

import com.society.dto.BookingRequestDTO;
import com.society.dto.BookingResponseDTO;
import com.society.entityenum.BookingStatus;

import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface BookingService {

    // USER / ADMIN
    BookingResponseDTO createBooking(
            BookingRequestDTO dto,
            HttpSession session);

    // USER / ADMIN
    List<BookingResponseDTO> myBookings(HttpSession session);

    // ADMIN
    BookingResponseDTO approveBooking(
            Integer bookingId,
            HttpSession session);

    // ADMIN
    BookingResponseDTO rejectBooking(
            Integer bookingId,
            HttpSession session);

    // USER / ADMIN
    BookingResponseDTO cancelBooking(
            Integer bookingId,
            HttpSession session);

    // ADMIN
    List<BookingResponseDTO> getAllBookings(
            BookingStatus status,
            HttpSession session);

	int myApprovedBookingCount(HttpSession session);
}
