package com.asein.workshop.vehicle.dto;

public record VehicleCreateRequest(
        Long customerId,
        String plateNumber,
        String brand,
        String model,
        Integer year,
        String engineNumber
) {
}
