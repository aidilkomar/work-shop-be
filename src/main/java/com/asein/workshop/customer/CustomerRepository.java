package com.asein.workshop.customer;

import com.asein.workshop.customer.dto.CustomerListResponse;
import com.asein.workshop.customer.dto.CustomerResponse;
import com.asein.workshop.customer.dto.CustomerRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class CustomerRepository {
    private final JdbcTemplate db;

    public CustomerRepository(JdbcTemplate db) {
        this.db = db;
    }

    public CustomerResponse save(Customer cust) {
        String sql = """
                INSERT INTO dev.customers (name, phone, address, created_at)
                VALUES (?, ?, ?, ?)
                """;
        db.update(sql, cust.getName(), cust.getPhone(), cust.getAddress(), cust.getCreatedAt());
        return new CustomerResponse(
                cust.getName(),
                cust.getPhone(),
                cust.getAddress(),
                cust.getCreatedAt().toString()
        );
    }

    public List<CustomerListResponse> findAll(int offset, int size, String name) {
        StringBuilder sql = new StringBuilder("""
                SELECT id, name, phone, address, created_at
                FROM dev.customers
                WHERE 1 = 1
                """);

        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            sql.append(" AND name = ? ");
            params.add(name);
        }

        sql.append("""
                ORDER by created_at
                LIMIT ? OFFSET ?
                """);

        params.add(size);
        params.add(offset);

        return db.query(
                sql.toString(),
                (rs, rowNum) -> new CustomerListResponse(
                        rs.getLong("id"),
                        new CustomerResponse(
                                rs.getString("name"),
                                rs.getString("phone"),
                                rs.getString("address"),
                                rs.getString("created_at")
                        )
                ),
                params.toArray()
        );
    }

    public Optional<Customer> findById(long id) {
        String sql = "SELECT name, phone, address, created_at FROM dev.customers WHERE id = ?";
        try {
            var cust = db.queryForObject(sql, new CustomerRowMapper(), id);
            assert cust != null;
            return Optional.of(cust);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> update(Customer c) {
        String sql = """
                UPDATE dev.customers
                SET name = ?, phone = ?, address = ?
                WHERE id = ?
                """;

        var rowAffected = db.update(sql, c.getName(), c.getPhone(), c.getAddress(), c.getId());
        if (rowAffected != 0) {
            return Optional.of(c);
        }

        return Optional.empty();
    }

    public boolean delete(long id) {
        String sql = """
                DELETE FROM dev.customers
                WHERE id = ?
                """;

        return db.update(sql, id) > 0;
    }
}
