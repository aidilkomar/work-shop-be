package com.asein.workshop.common;

public record GrantPermissionDto(
        Long roleId,
        Long featureId
) {}

