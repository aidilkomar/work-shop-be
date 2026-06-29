package com.asein.workshop.mechanic.dto;

import com.asein.workshop.common.PagedRequest;

public record MechanicListRequest(
        PagedRequest pageReq,
        String name
) {
}
