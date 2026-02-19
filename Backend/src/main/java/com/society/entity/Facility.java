package com.society.entity;

/* ========= JPA ========= */
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;      // ✅ ADDED
import jakarta.persistence.FetchType;     // ✅ ADDED
import jakarta.persistence.JoinColumn;    // ✅ ADDED

/* ========= LOMBOK ========= */
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/* ========= ENTITY ENUM ========= */
import com.society.entityenum.FacilityName;
import com.society.entityenum.FacilityStatus;

import java.time.LocalDate;

@Entity
@Table(name = "facility")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facility extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer facilityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FacilityName facilityName;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Boolean bookingRequired;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FacilityStatus status;

    private LocalDate availableFrom;
    private LocalDate availableTo;

    // ADDED FOR MULTIPLE SOCIETIES SUPPORT
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id", nullable = false)
    private Society society;

}
