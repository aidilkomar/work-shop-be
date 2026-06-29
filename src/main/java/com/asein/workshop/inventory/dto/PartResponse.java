package com.asein.workshop.inventory.dto;

import java.math.BigDecimal;

public record PartResponse(
        Long id,
        String sku,
        String name,
        BigDecimal purchasePrice,
        BigDecimal sellingPrice,
        Integer stock,
        Integer minStock,
        Boolean isActive
) {
}
