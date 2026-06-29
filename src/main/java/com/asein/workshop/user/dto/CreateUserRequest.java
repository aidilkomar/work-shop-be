package com.asein.workshop.user.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "username is required") String username,
        @NotBlank(message = "password is required") String password,
        @NotBlank(message = "fullName is required") String fullName,
        @NotBlank(message = "role is required") String role,
        Boolean isActive
) {
}
