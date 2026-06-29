package com.asein.workshop.vehicle.dto;

public record VehicleUpdateRequest(
        long id,
        VehicleCreateRequest vehicle
) {
}
