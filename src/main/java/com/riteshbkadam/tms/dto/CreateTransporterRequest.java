package com.riteshbkadam.tms.dto;

import com.riteshbkadam.tms.utils.AvailableTrucks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransporterRequest {
    private String companyName;
    private double rating;
    private List<AvailableTrucks> availableTrucks;
}