package com.asein.workshop.inventory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
