package com.riteshbkadam.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransporterRequest {
    private String companyName;
    private double rating;
    private List<AvailableTruckRequest> availableTrucks;
}
