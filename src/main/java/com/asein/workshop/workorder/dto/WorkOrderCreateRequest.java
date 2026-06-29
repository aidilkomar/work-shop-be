package com.asein.workshop.workorder.dto;

import jakarta.validation.constraints.NotNull;

public record WorkOrderCreateRequest(
        @NotNull(message = "vehicleId is required") Long vehicleId,

        @NotNull(message = "mechanicId is required") Long mechanicId,

        String type,

        String complaint
) {
}
