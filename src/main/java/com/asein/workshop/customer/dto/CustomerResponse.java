package com.asein.workshop.customer.dto;

public record CustomerResponse(
        String name,
        String phone,
        String address,
        String createdAt
) {
}
