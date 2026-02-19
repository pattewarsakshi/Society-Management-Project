package com.society.entity;

import java.time.LocalDateTime;

import com.society.entityenum.VehicleType;
import com.society.entityenum.VisitPurpose;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visitor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer visitorId;

    @Column(nullable = false)
    private String visitorName;

    private String phone;

    @Enumerated(EnumType.STRING)
    private VisitPurpose purpose;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    @Column(nullable = true)
    private String visitorPhoto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private VehicleType vehicleType;

    @Column(nullable = true)
    private String vehicleNumber;

    
    @Column(nullable = false)
    private Long flatId;

    @OneToOne(mappedBy = "visitor", fetch = FetchType.LAZY)
    private ParkingSlot parkingSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id")
    private Society society;
}
