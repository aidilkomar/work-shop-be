package com.asein.workshop.inventory;

import com.asein.workshop.inventory.dto.PartRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PartRepository {
    private final JdbcTemplate db;

    public PartRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Optional<Part> findBySku(String sku) {
        String sql = """
                SELECT id, sku, name, purchase_price, selling_price, stock, min_stock, is_active, created_at, updated_at
                FROM parts
                WHERE sku = ?
                """;

        return Optional.ofNullable(db.queryForObject(sql, new PartRowMapper(), sku));
    }
}
