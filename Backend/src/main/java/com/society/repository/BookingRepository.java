package com.society.repository;

import com.society.entity.Booking;
import com.society.entity.Facility;
import com.society.entityenum.BookingStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // Overlap check for same facility, date and time slot
    @Query("""
        SELECT b FROM Booking b
        WHERE b.facility = :facility
          AND b.bookingDate = :date
          AND b.status IN ('PENDING','APPROVED')
          AND (:start < b.endTime AND :end > b.startTime)
    """)
    List<Booking> findConflictingBookings(
            @Param("facility") Facility facility,
            @Param("date") LocalDate date,
            @Param("start") LocalTime start,
            @Param("end") LocalTime end);

    // Fetch bookings of a particular user
    List<Booking> findByUser_UserId(Integer userId);

	int countByUser_UserIdAndStatus(Integer userId, BookingStatus approved);
}
