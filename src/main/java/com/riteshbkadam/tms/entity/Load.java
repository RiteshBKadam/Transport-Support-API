package com.riteshbkadam.tms.entity;

import com.riteshbkadam.tms.utils.status.BidStatus;
import com.riteshbkadam.tms.utils.status.LoadStatus;
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

public class Load {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID loadId;
    private String shipperId;
    private String loadingCity;
    private String unloadingCity;
    private String productType;
    private double weight;
    private String weightUnit;
    private Timestamp loadingDate;
    private String truckType;
    private int noOfTrucks;
    @Enumerated(EnumType.STRING)
    private LoadStatus status;
    private Timestamp datePosted;
    @Version
    private Long version;


}