package com.asein.workshop.customer.dto;

public record CustomerUpdateRequest(
        long id,
        CustomerCreateRequest customer
) {
}
