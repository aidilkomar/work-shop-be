package com.asein.workshop.workorder;

import com.asein.workshop.workorder.dto.WorkOrderListResponse;
import com.asein.workshop.workorder.dto.WorkOrderRowMapper;
import com.asein.workshop.workorder.dto.WorkOrderResponse;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkOrderRepository {
    private final JdbcTemplate db;

    public WorkOrderRepository(JdbcTemplate db) {
        this.db = db;
    }

    public WorkOrderResponse save(WorkOrder wo) {
        String sql;
        Object[] params;

        if (wo.getId() == 0) {
            sql = """
                    INSERT INTO work_orders(vehicle_id, mechanic_id, "type", status, complaint, created_at)
                    VALUES(?, ?, ?, ?, ?, ?)
                    RETURNING id, wo_number, vehicle_id, mechanic_id, "type", status, complaint, created_at
                    """;

            params = new Object[] {
                    wo.getVehicleId(),
                    wo.getMechanicId(),
                    wo.getType(),
                    wo.getStatus(),
                    wo.getComplaint(),
                    wo.getCreatedAt()
            };
        } else {
            sql = """
                    UPDATE work_orders
                    SET vehicle_id = ?,
                        mechanic_id = ?,
                        "type" = ?,
                        status = ?,
                        complaint = ?,
                        updated_at = ?)
                    WHERE id = ?
                    RETURNING id, wo_number, vehicle_id, mechanic_id, "type", status, complaint, updated_at
                    """;

            params = new Object[] {
                    wo.getVehicleId(),
                    wo.getMechanicId(),
                    wo.getType(),
                    wo.getStatus(),
                    wo.getComplaint(),
                    wo.getUpdatedAt()
            };
        }

        return db.queryForObject(
                sql,
                (rs, rowNum) -> new WorkOrderResponse(
                        rs.getLong("id"),
                        rs.getString("wo_number"),
                        rs.getLong("vehicle_id"),
                        rs.getLong("mechanic_id"),
                        rs.getString("type"),
                        rs.getString("status"),
                        rs.getString("complaint")
                ),
                params
        );
    }

    public Optional<WorkOrder> findById(long id) {
        String sql = """
                SELECT wo.id, wo.wo_number, wo.vehicle_id, wo.mechanic_id, wo."type" ,wo.status , wo.complaint ,wo.created_at
                FROM work_orders wo
                WHERE wo.id = ?
                """;

        return Optional.ofNullable(db.queryForObject(sql, new WorkOrderRowMapper(), id));
    }

    public List<WorkOrderResponse> findAllByMechanicId(long id) {
        String sql = """
                SELECT id, wo_number, vehicle_id, mechanic_id, type, status, complaint, created_at
                FROM work_orders
                WHERE id = ?
                """;

        return db.query(sql, new WorkOrderRowMapper(), id)
                .stream().map(wo -> new WorkOrderResponse(
                        wo.getId(),
                        wo.getWoNumber(),
                        wo.getVehicleId().longValue(),
                        wo.getMechanicId().longValue(),
                        wo.getType(),
                        wo.getStatus(),
                        wo.getComplaint()
                ))
                .toList();
    }

    public List<WorkOrderListResponse> findAll(int offset, int limit, String s) {
        StringBuilder sql = new StringBuilder("""
                SELECT wo.id, wo.wo_number, v.plate_number, u.full_name, wo.type, wo.status, wo.complaint, wo.created_at
                FROM work_orders wo
                JOIN mechanics m ON m.id = wo.mechanic_id
                JOIN vehicles v ON v.id = wo.vehicle_id
                JOIN users u ON u.id = m.user_id
                WHERE 1 = 1
                """);

        List<Object> params = new ArrayList<>();

        if (!s.isEmpty()) {
            sql.append(" AND u.full_name like %?%");
            params.add(s);
        }

        sql.append(" ORDER BY wo.id ASC");
        sql.append(" limit ? offset ?");

        params.add(limit);
        params.add(offset);

        return db.query(sql.toString(), (rs, rowNum) ->
                new WorkOrderListResponse(
                        rs.getLong("id"),
                        rs.getString("wo_number"),
                        rs.getString("plate_number"),
                        rs.getString("full_name"),
                        rs.getString("type"),
                        rs.getString("status"),
                        rs.getString("compliant")
                ),
                params.toArray()
        );
    }

    public void updateStatus(long id, WorkOrderStatus nextStatus) {
        String sql = """
                UPDATE work_orders
                SET status = ?
                WHERE id = ?
                """;
        db.update(sql, nextStatus.name(), id);
    }
}
