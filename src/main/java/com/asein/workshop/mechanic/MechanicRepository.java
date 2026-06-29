package com.asein.workshop.mechanic;

import com.asein.workshop.mechanic.dto.MechanicListResponse;
import com.asein.workshop.mechanic.dto.MechanicResponse;
import com.asein.workshop.mechanic.dto.MechanicRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class MechanicRepository {
    private final JdbcTemplate db;

    public MechanicRepository(JdbcTemplate db) {
        this.db = db;
    }

    public List<MechanicListResponse> findAll(int offset, int limit, String name) {
        StringBuilder sql = new StringBuilder("""
                SELECT m.id, u.full_name, m.user_id, m.status, m.specialization, m.hire_date, m.salary, m.skill_level
                FROM mechanics m
                JOIN users u ON u.id = m.user_id
                WHERE 1 = 1
                """);

        List<Object> params = new ArrayList<>();

        if (name.isEmpty()) {
            sql.append(" AND u.full_name = ?");
            params.add(name);
        }

        sql.append("""
                ORDER by created_at
                LIMIT ? OFFSET ?
                """);

        params.add(limit);
        params.add(offset);

        return db.query(
                sql.toString(),
                (rs,rowNum) -> new MechanicListResponse(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("full_name"),
                        rs.getString("status"),
                        rs.getString("specialization"),
                        rs.getTimestamp("hire_date"),
                        rs.getBigDecimal("salary"),
                        rs.getObject("skill_level", Integer.class)
                ),
                params.toArray()
        );
    }

    public Optional<Mechanic> findById(long id) {
        String sql = """
                SELECT id, user_id, status, specialization, hire_date, salary, skill_level
                FROM mechanics
                WHERE id = ?
                """;

        var m = db.queryForObject(sql, new MechanicRowMapper(), id);

        return Optional.ofNullable(m);
    }

    public MechanicResponse save(Mechanic m) {
        String sql;
        Object[] params;

        if (m.getId() == 0) {

            sql = """
                INSERT INTO mechanic
                (
                    user_id,
                    status,
                    specialization,
                    hire_date,
                    salary,
                    skill_level
                )
                VALUES (?, ?, ?, ?, ?, ?)
                RETURNING
                    id,
                    user_id,
                    status,
                    specialization,
                    hire_date,
                    salary,
                    skill_level
                """;

            params = new Object[]{
                    m.getUserId(),
                    m.getStatus(),
                    m.getSpecialization(),
                    m.getHireDate(),
                    m.getSalary(),
                    m.getSkillLevel()
            };

        } else {

            sql = """
                UPDATE vehicles
                SET user_id = ?,
                    status = ?,
                    specialization = ?,
                    hire_date = ?,
                    salary = ?,
                    skill_level = ?
                WHERE id = ?
                RETURNING
                    id,
                    user_id,
                    status,
                    specialization,
                    hire_date,
                    salary,
                    skill_level
                """;

            params = new Object[]{
                    m.getUserId(),
                    m.getStatus(),
                    m.getSpecialization(),
                    m.getHireDate(),
                    m.getSalary(),
                    m.getSkillLevel(),
                    m.getId()
            };
        }

        return db.queryForObject(
                sql,
                (rs, rowNum) -> new MechanicResponse(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("status"),
                        rs.getString("specialization"),
                        rs.getTimestamp("hire_date"),
                        rs.getObject("salary", BigDecimal.class),
                        rs.getObject("skill_level", Integer.class)
                ),
                params
        );
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM mechanics WHERE id = ?";
        return db.update(sql, id) > 0;
    }
}
