package com.asein.workshop.common;

public record NavigationResponseDto(
        String feature,
        String path,
        String icon,
        Integer sortOrder
) {
}
