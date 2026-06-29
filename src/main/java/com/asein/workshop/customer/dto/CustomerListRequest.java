package com.asein.workshop.customer.dto;

import com.asein.workshop.common.PagedRequest;

public record CustomerListRequest(
        PagedRequest pageReq,
        String name
) {
}
