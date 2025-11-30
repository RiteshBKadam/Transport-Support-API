package com.riteshbkadam.tms.entity;

import com.riteshbkadam.tms.utils.status.BidStatus;
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
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bidId;
    private UUID transporterId;
    private UUID loadId;
    private double proposedRate;
    private int trucksOffered;
    @Enumerated(EnumType.STRING)
    private BidStatus status;
    private Timestamp submittedAt;
}
