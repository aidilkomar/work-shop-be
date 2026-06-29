package com.asein.workshop.inventory.dto;

import com.asein.workshop.common.PagedRequest;

public record PartListRequest(
        PagedRequest pageReq,
        String name
) {
}
