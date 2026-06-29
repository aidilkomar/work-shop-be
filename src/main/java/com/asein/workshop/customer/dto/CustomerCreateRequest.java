package com.asein.workshop.customer.dto;

public record CustomerCreateRequest(
        String name,
        String phone,
        String address
) {
}
