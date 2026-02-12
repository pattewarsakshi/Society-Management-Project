package com.society.service;

import com.society.dto.BookingRequestDTO;
import com.society.dto.BookingResponseDTO;
import com.society.entity.Booking;
import com.society.entity.Facility;
import com.society.entity.User;
import com.society.entityenum.BookingStatus;
import com.society.entityenum.FacilityStatus;
import com.society.entityenum.Role;
import com.society.repository.BookingRepository;
import com.society.repository.FacilityRepository;
import com.society.repository.UserRepository;
import com.society.util.LoggedInUserUtil;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FacilityRepository facilityRepository;
    private final LoggedInUserUtil loggedInUserUtil;
    private final UserRepository userRepository;
    

    // CREATE BOOKING
    @Override
    @Transactional
    public BookingResponseDTO createBooking(
            BookingRequestDTO dto,
            HttpSession session) {

        User user = loggedInUserUtil.getUser(session);

        if (user.getRole() == Role.GUARD) {
            throw new RuntimeException("Guard cannot book facilities");
        }

        Facility facility = facilityRepository.findById(dto.getFacilityId())
                .orElseThrow(() -> new RuntimeException("Facility not found"));

        if (facility.getStatus() != FacilityStatus.ACTIVE) {
            throw new RuntimeException("Facility is not active");
        }

        if (!facility.getBookingRequired()) {
            throw new RuntimeException("Booking not required for this facility");
        }

        if (!dto.getEndTime().isAfter(dto.getStartTime())) {
            throw new RuntimeException("End time must be after start time");
        }

        if (!bookingRepository.findConflictingBookings(
                facility,
                dto.getBookingDate(),
                dto.getStartTime(),
                dto.getEndTime()
        ).isEmpty()) {
            throw new RuntimeException("Time slot already booked");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setFacility(facility);
        booking.setBookingDate(dto.getBookingDate());
        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());
        booking.setPurpose(dto.getPurpose());
        booking.setStatus(BookingStatus.PENDING);

        return map(bookingRepository.save(booking));
    }

    //VIEW MY BOOKINGS 
    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> myBookings(HttpSession session) {

        User user = loggedInUserUtil.getUser(session);

        if (user.getRole() == Role.GUARD) {
            throw new RuntimeException("Guard cannot view bookings");
        }

        return bookingRepository.findByUser_UserId(user.getUserId())
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    //  ADMIN: APPROVe
    @Override
    @Transactional
    public BookingResponseDTO approveBooking(
            Integer bookingId,
            HttpSession session) {

        User admin = loggedInUserUtil.getUser(session);

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin can approve booking");
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.APPROVED);
        return map(bookingRepository.save(booking));
    }

    //  ADMIN: REJECT 
    @Override
    @Transactional
    public BookingResponseDTO rejectBooking(
            Integer bookingId,
            HttpSession session) {

        User admin = loggedInUserUtil.getUser(session);

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin can reject booking");
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.REJECTED);
        return map(bookingRepository.save(booking));
    }

    // USER / ADMIN: CANCEL 
    @Override
    @Transactional
    public BookingResponseDTO cancelBooking(
            Integer bookingId,
            HttpSession session) {

        User user = loggedInUserUtil.getUser(session);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUser().getUserId().equals(user.getUserId())
                && user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Not allowed to cancel this booking");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return map(bookingRepository.save(booking));
    }

    // ADMIN: VIEW ALL 
    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getAllBookings(
            BookingStatus status,
            HttpSession session) {

        User admin = loggedInUserUtil.getUser(session);

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin can view all bookings");
        }

        return bookingRepository.findAll()
                .stream()
                .filter(b -> status == null || b.getStatus() == status)
                .map(this::map)
                .collect(Collectors.toList());
    }

    //  MAPPER 
    private BookingResponseDTO map(Booking b) {
        return new BookingResponseDTO(
                b.getBookingId(),
                b.getFacility().getFacilityId(),
                b.getFacility().getFacilityName().name(),
                b.getBookingDate(),
                b.getStartTime(),
                b.getEndTime(),
                b.getStatus().name(),
                b.getUser().getUserId(),
                b.getUser().getFirstName() + " " + b.getUser().getLastName(),
                b.getPurpose()
        );
    }
    
    @Override
    @Transactional(readOnly = true)
    public int myApprovedBookingCount(HttpSession session) {
        User user = loggedInUserUtil.getUser(session);

        if (user.getRole() == Role.GUARD) {
            throw new RuntimeException("Guard cannot view bookings");
        }

        return bookingRepository.countByUser_UserIdAndStatus(
                user.getUserId(),
                BookingStatus.APPROVED
        );
    }

}
