package com.society.controller;

import com.society.dto.BookingRequestDTO;
import com.society.dto.BookingResponseDTO;
import com.society.entityenum.BookingStatus;
import com.society.service.BookingService;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin
public class BookingController {

    private final BookingService bookingService;

    // MEMBER + ADMIN
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @RequestBody BookingRequestDTO dto,
            HttpSession session) {

        return ResponseEntity.ok(
                bookingService.createBooking(dto, session));
    }

    // MEMBER + ADMIN
    @GetMapping("/my")
    public ResponseEntity<List<BookingResponseDTO>> myBookings(
            HttpSession session) {

        return ResponseEntity.ok(
                bookingService.myBookings(session));
    }

    // ADMIN
    @PutMapping("/{id}/approve")
    public ResponseEntity<BookingResponseDTO> approve(
            @PathVariable Integer id,
            HttpSession session) {

        return ResponseEntity.ok(
                bookingService.approveBooking(id, session));
    }

    // ADMIN
    @PutMapping("/{id}/reject")
    public ResponseEntity<BookingResponseDTO> reject(
            @PathVariable Integer id,
            HttpSession session) {

        return ResponseEntity.ok(
                bookingService.rejectBooking(id, session));
    }

    // USER + ADMIN
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancel(
            @PathVariable Integer id,
            HttpSession session) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(id, session));
    }

    // ADMIN
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAll(
            @RequestParam(required = false) BookingStatus status,
            HttpSession session) {

        return ResponseEntity.ok(
                bookingService.getAllBookings(status, session));
    }
    
	 // MEMBER
	    @GetMapping("/my/approved/count")
	    public ResponseEntity<Integer> myApprovedBookingCount(HttpSession session) {
	        return ResponseEntity.ok(
	                bookingService.myApprovedBookingCount(session)
	        );
	    }

}
