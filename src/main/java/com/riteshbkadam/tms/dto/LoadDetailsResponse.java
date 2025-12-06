package com.riteshbkadam.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadDetailsResponse {
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
    private String status;
    private Timestamp datePosted;
    private List<BidResponse> bids;
}
