package com.riteshbkadam.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBidRequest {
    private UUID loadId;
    private UUID transporterId;
    private double proposedRate;
    private int trucksOffered;
}
