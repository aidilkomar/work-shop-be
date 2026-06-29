package com.asein.workshop.user.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(
        @NotNull(message = "id is required") long id,
        CreateUserRequest user
) {
}
