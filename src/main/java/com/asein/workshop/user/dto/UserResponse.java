package com.asein.workshop.user.dto;

public record UserResponse(
        String username,
        String fullName,
        String role,
        Boolean isActive
) {
}
