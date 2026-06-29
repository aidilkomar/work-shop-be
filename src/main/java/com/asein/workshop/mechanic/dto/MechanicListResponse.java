package com.asein.workshop.mechanic.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record MechanicListResponse(
        long id,
        long userId,
        String name,
        String status,
        String specialization,
        Timestamp hireDate,
        BigDecimal salary,
        int skillLevel
) {
}
