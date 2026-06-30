package com.asein.workshop.inventory;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Part {
    private Long id;

    private String sku;

    private String name;

    private BigDecimal purchasePrice;

    private BigDecimal sellingPrice;

    private Integer stock;

    private Integer minStock;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
