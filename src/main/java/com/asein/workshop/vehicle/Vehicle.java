package com.asein.workshop.vehicle;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Vehicle {
    private Long id;

    private Long customerId;

    private String plateNumber;

    private String brand;

    private String model;

    private Integer year;

    private String engineNumber;

    private LocalDateTime createdAt;
}
