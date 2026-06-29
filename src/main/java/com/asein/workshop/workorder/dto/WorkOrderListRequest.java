package com.asein.workshop.workorder.dto;

import com.asein.workshop.common.PagedRequest;

public record WorkOrderListRequest(
        PagedRequest pageReq,
        String plateNumber
) {
}
