package com.society.entity;

import java.time.LocalDate;

import com.society.entityenum.ComplaintStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "complaint")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Complaint extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer complaintId;

    // Resident who raised complaint
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Flat for which complaint is raised
    @ManyToOne
    @JoinColumn(name = "flat_id", nullable = false)
    private Flat flat;

    @Column(nullable = false)
    private String complaintType;

    @Column(nullable = false, length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus status;

    @Column(nullable = false)
    private LocalDate dateFiled;

    private LocalDate dateResolved;
}
