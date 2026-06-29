package com.asein.workshop.vehicle.dto;

public record VehicleResponse(
        Long id,
        Long customerId,
        String plateNumber,
        String brand,
        String model,
        Integer year,
        String engineNumber
) {
}
