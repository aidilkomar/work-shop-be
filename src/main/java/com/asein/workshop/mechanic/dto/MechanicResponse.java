package com.asein.workshop.mechanic.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record MechanicResponse(
        long id,
        long userId,
        String status,
        String specialization,
        Timestamp hireDate,
        BigDecimal salary,
        int skillLevel
) {
}
