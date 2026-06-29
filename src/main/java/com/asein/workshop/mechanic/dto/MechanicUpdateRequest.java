package com.asein.workshop.mechanic.dto;

public record MechanicUpdateRequest(
        long id,
        MechanicCreateRequest mechanic
) {
}
