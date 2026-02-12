package com.society.entity;

import java.util.List;

import com.society.entityenum.FlatStatus;
import com.society.entityenum.ParkingType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "flat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flatId;

    @Column(nullable = false, length = 10)
    private String flatNumber;

    @Column(nullable = false, length = 10)
    private String towerName;

    @Column
    private Double areaSqft;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlatStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "society_id", nullable = false)
    private Society society;

    // ✅ RESIDENT parking slots
    @OneToMany(mappedBy = "flat", fetch = FetchType.EAGER)
    private List<ParkingSlot> parkingSlots;
    
    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = FlatStatus.VACANT;
        }
    }
}

