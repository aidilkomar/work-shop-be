package com.asein.workshop.vehicle.dto;

import com.asein.workshop.vehicle.Vehicle;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleRowMapper implements RowMapper<Vehicle> {
    @Override
    public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Vehicle.builder()
                .id(rs.getLong("id"))
                .plateNumber(rs.getString("plate_number"))
                .brand(rs.getString("brand"))
                .model(rs.getString("model"))
                .year(rs.getObject("year", Integer.class))
                .engineNumber(rs.getString("engine_number"))
                .build();
    }
}
