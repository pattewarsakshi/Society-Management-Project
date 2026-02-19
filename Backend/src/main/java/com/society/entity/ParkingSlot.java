package com.society.entity;

import com.society.entityenum.ParkingStatus;
import com.society.entityenum.ParkingType;
import com.society.entityenum.VehicleType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSlot extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer parkingId;

	@Column(nullable = false)
	private String slotNumber;

	// 🚗 TWO_WHEELER / FOUR_WHEELER
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private VehicleType vehicleType;

	// 🅿️ RESIDENT / VISITOR
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ParkingType parkingType;

	// ✅ FREE / OCCUPIED (THIS replaces occupied boolean)
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ParkingStatus status = ParkingStatus.FREE;

	// 🔗 Resident parking (only if parkingType = RESIDENT)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "flat_id")
	private Flat flat;

	// 🔗 Visitor parking (only if parkingType = VISITOR)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visitor_id")
	private Visitor visitor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "society_id", nullable = false)
	private Society society;
}
