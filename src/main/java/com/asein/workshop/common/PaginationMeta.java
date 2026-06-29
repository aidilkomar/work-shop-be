package com.asein.workshop.common;

public record PaginationMeta(
        long totalItems,
        int totalPages,
        int page,
        int size) {
}
