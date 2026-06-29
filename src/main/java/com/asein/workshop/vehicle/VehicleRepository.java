package com.asein.workshop.vehicle;

import com.asein.workshop.customer.dto.CustomerListResponse;
import com.asein.workshop.customer.dto.CustomerResponse;
import com.asein.workshop.customer.dto.CustomerRowMapper;
import com.asein.workshop.vehicle.dto.VehicleListRequest;
import com.asein.workshop.vehicle.dto.VehicleResponse;
import com.asein.workshop.vehicle.dto.VehicleRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class VehicleRepository {
    private final JdbcTemplate db;

    public VehicleRepository(JdbcTemplate db) {
        this.db = db;
    }

    public List<VehicleResponse> findAll(int offset, int size, VehicleListRequest req) {
        StringBuilder sql = new StringBuilder("""
                SELECT customer_id , plate_number , brand , model ,"year" ,engine_number ,created_at
                FROM vehicles
                WHERE 1 = 1
                """);
        List<Object> params = new ArrayList<>();

//        SELECT id, user_id , status, specialization, hire_date, salary, skill_level
//        FROM mechanics
        if (req.platNumber() != null && !req.platNumber().isEmpty()) {
            sql.append(" AND plate_number = ? ");
            params.add(req.platNumber());
        }

        sql.append("""
                ORDER by created_at
                LIMIT ? OFFSET ?
                """);

        params.add(size);
        params.add(offset);

        return db.query(
                sql.toString(),
                (rs, rowNum) -> new VehicleResponse(
                        rs.getLong("id"),
                        rs.getLong("customer_id"),
                        rs.getString("plate_number"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getObject("year", Integer.class),
                        rs.getString("engine_number")
                ),
                params.toArray()
        );
    }

    public Optional<Vehicle> findById(long id) {
        String sql = """
                SELECT customer_id , plate_number , brand , model ,"year" ,engine_number ,created_at
                FROM vehicles
                WHERE id = ?
                """;

        try {
            var v = db.queryForObject(sql, new VehicleRowMapper(), id);
            assert v != null;
            return Optional.of(v);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Vehicle> findByPlatNumber(String s) {
        String sql = """
                SELECT customer_id , plate_number , brand , model ,"year" ,engine_number ,created_at
                FROM vehicles
                WHERE plate_number = ?
                """;

        try {
            var v = db.queryForObject(sql, new VehicleRowMapper(), s);
            assert v != null;
            return Optional.of(v);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public VehicleResponse save(Vehicle v) {
        String sql;
        Object[] params;

        if (v.getId() == null || v.getId() == 0) {

            sql = """
                INSERT INTO vehicles
                (
                    customer_id,
                    plate_number,
                    brand,
                    model,
                    year,
                    engine_number
                )
                VALUES (?, ?, ?, ?, ?, ?)
                RETURNING
                    id,
                    customer_id,
                    plate_number,
                    brand,
                    model,
                    year,
                    engine_number,
                    created_at
                """;

            params = new Object[]{
                    v.getCustomerId(),
                    v.getPlateNumber(),
                    v.getBrand(),
                    v.getModel(),
                    v.getYear(),
                    v.getEngineNumber()
            };

        } else {

            sql = """
                UPDATE vehicles
                SET customer_id = ?,
                    plate_number = ?,
                    brand = ?,
                    model = ?,
                    year = ?,
                    engine_number = ?
                WHERE id = ?
                RETURNING
                    id,
                    customer_id,
                    plate_number,
                    brand,
                    model,
                    year,
                    engine_number,
                    created_at
                """;

            params = new Object[]{
                    v.getCustomerId(),
                    v.getPlateNumber(),
                    v.getBrand(),
                    v.getModel(),
                    v.getYear(),
                    v.getEngineNumber(),
                    v.getId()
            };
        }

        return db.queryForObject(
                sql,
                (rs, rowNum) -> new VehicleResponse(
                        rs.getLong("id"),
                        rs.getLong("customer_id"),
                        rs.getString("plate_number"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getObject("year", Integer.class),
                        rs.getString("engine_number")
                ),
                params
        );
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM vehicles WHERE id = ?";
        return db.update(sql, id) > 0;
    }
}
