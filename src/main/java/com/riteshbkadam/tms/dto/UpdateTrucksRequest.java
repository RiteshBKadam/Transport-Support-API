package com.riteshbkadam.tms.dto;

import java.util.List;

public record UpdateTrucksRequest(
        List<AvailableTruckRequest> availableTrucks
) {}
