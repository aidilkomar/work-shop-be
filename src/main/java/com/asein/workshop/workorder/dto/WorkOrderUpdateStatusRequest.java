package com.asein.workshop.workorder.dto;

import com.asein.workshop.workorder.WorkOrderStatus;

public record WorkOrderUpdateStatusRequest(
        WorkOrderStatus status
) {
}
