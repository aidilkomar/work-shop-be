package com.asein.workshop.mechanic.dto;

import com.asein.workshop.mechanic.Mechanic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MechanicRowMapper implements RowMapper<Mechanic> {
    @Override
    public Mechanic mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Mechanic.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .status(rs.getString("status"))
                .specialization(rs.getString("specialization"))
                .hireDate(rs.getTimestamp("hire_date"))
                .salary(rs.getBigDecimal("salary"))
                .skillLevel(rs.getObject("skill_level", Integer.class))
                .build();
    }
}
