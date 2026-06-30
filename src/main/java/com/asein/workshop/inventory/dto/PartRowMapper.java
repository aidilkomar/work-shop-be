package com.asein.workshop.inventory.dto;

import com.asein.workshop.inventory.Part;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PartRowMapper implements RowMapper<Part> {
    @Override
    public Part mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Part.builder()
                .id(rs.getLong("id"))
                .sku(rs.getString("sku"))
                .name(rs.getString("name"))
                .stock(rs.getObject("stock", Integer.class))
                .minStock(rs.getObject("min_stock", Integer.class))
                .purchasePrice(rs.getBigDecimal("purchase_price"))
                .sellingPrice(rs.getBigDecimal("selling_price"))
                .active(rs.getBoolean("is_active"))
                .createdAt(LocalDateTime.from(rs.getTimestamp("created_at").toInstant()))
                .updatedAt(LocalDateTime.from(rs.getTimestamp("updated_at").toInstant()))
                .build();
    }
}
