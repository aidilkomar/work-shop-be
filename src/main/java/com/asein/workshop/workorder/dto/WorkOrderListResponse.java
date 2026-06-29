package com.asein.workshop.workorder.dto;

public record WorkOrderListResponse(
        Long id,

        String woNumber,

        String plateNumber,

        String mechanicName,

        String type,

        String status,

        String complaint
) {
}
