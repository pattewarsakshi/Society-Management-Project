package com.society.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "society")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Society  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer societyId;

    @Column(nullable = false, unique = true)
    private String societyName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, length = 6)
    private String pincode;
}
