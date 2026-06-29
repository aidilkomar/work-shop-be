package com.asein.workshop.user.dto;

import com.asein.workshop.common.PagedRequest;

public record UserListRequest(
        PagedRequest pageReq,
        String username,
        String fullName
) {
}
