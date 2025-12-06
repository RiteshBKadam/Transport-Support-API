package com.riteshbkadam.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableTruckRequest {
    private String truckType;
    private int count;
}
