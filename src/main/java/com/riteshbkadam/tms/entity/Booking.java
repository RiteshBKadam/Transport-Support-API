package com.riteshbkadam.tms.entity;


import com.riteshbkadam.tms.utils.status.BidStatus;
import com.riteshbkadam.tms.utils.status.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bookingId;
    private UUID loadId;
    private UUID bidId;
    private UUID transporterId;
    private int allocatedTrucks;
    private double finalRate;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private Timestamp bookedAt;
}
