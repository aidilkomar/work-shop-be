package com.asein.workshop.mechanic.dto;

public record MechanicCreateRequest(
        long userId,
        String specialization,
        String hireDate,
        String salary,
        int skillLevel
) {
}
