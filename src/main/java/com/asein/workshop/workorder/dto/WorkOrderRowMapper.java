package com.asein.workshop.workorder.dto;

import com.asein.workshop.workorder.WorkOrder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkOrderRowMapper implements RowMapper<WorkOrder> {
    @Override
    public WorkOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        return WorkOrder.builder()
                .id(rs.getLong("id"))
                .vehicleId(rs.getObject("vehicle_id", Integer.class))
                .mechanicId(rs.getObject("mechanic_id", Integer.class))
                .woNumber(rs.getString("wo_number"))
                .type(rs.getString("type"))
                .status(rs.getString("status"))
                .complaint(rs.getString("complaint"))
                .createdAt(rs.getTimestamp("created_at"))
                .build();
    }
}
