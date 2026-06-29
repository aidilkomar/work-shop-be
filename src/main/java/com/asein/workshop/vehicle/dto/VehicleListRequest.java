package com.asein.workshop.vehicle.dto;

import com.asein.workshop.common.PagedRequest;

public record VehicleListRequest(
        PagedRequest pageReq,
        String platNumber
) {
}
