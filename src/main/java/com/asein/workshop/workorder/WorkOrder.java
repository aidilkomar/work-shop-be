package com.asein.workshop.workorder;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class WorkOrder {
    private Long id;
    private String woNumber;
    private Integer vehicleId;
    private Integer mechanicId;
    private String type;
    private String status;
    private String complaint;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
