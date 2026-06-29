package com.asein.workshop.customer.dto;

public record CustomerListResponse(
        long id,
        CustomerResponse customer
) {
}
