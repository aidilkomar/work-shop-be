package com.asein.workshop.mechanic;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class Mechanic {
    private Long id;
    private Long userId;
    private String status;
    private String specialization;
    private Timestamp hireDate;
    private BigDecimal salary;
    private int skillLevel;
}
