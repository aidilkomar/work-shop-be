package com.asein.workshop.workorder.dto;

public record WorkOrderResponse(

        Long id,

        String woNumber,

        Long vehicleId,

        Long mechanicId,

        String type,

        String status,

        String complaint
) {
}
