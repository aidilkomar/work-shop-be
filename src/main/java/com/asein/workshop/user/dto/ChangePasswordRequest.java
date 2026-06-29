package com.asein.workshop.user.dto;

public record ChangePasswordRequest(
        long id,
        String password,
        String confirmPassword
) {
}
