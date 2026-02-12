package com.society.entity;

/* ========= JPA ========= */
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

/* ========= LOMBOK ========= */
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import com.society.entityenum.FacilityName;
import com.society.entityenum.FacilityStatus;

import java.time.LocalDate;
/* ========= JAVA ========= */
import java.util.List;

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


//    @OneToMany(mappedBy = "facility", fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<Booking> bookings;
}
