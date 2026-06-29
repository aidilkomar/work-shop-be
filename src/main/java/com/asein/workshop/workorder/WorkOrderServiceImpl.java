package com.asein.workshop.workorder;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.common.exception.BusinessException;
import com.asein.workshop.common.exception.NotFoundException;
import com.asein.workshop.mechanic.MechanicRepository;
import com.asein.workshop.vehicle.VehicleRepository;
import com.asein.workshop.workorder.dto.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WorkOrderServiceImpl implements WorkOrderService {
    private final WorkOrderRepository workOrderRepository;
    private final VehicleRepository vehicleRepository;
    private final MechanicRepository mechanicRepository;

    public WorkOrderServiceImpl(WorkOrderRepository workOrderRepository, VehicleRepository vehicleRepository, MechanicRepository mechanicRepository) {
        this.workOrderRepository = workOrderRepository;
        this.vehicleRepository = vehicleRepository;
        this.mechanicRepository = mechanicRepository;
    }

    @Override
    public WorkOrderResponse create(WorkOrderCreateRequest req) {
        var vehicle = vehicleRepository.findById(req.vehicleId());
        if (vehicle.isEmpty()) {
            throw new NotFoundException("data vehicle not found");
        }

        var mechanic = mechanicRepository.findById(req.mechanicId());
        if (mechanic.isEmpty()) {
            throw new NotFoundException("data mechanic not found");
        }

        var wo = WorkOrder.builder()
                .vehicleId(req.vehicleId() != 0 ? req.vehicleId().intValue() : null)
                .mechanicId(req.mechanicId() != 0 ? req.mechanicId().intValue() : null)
                .type(req.type())
                .complaint(req.complaint())
                .status(WorkOrderStatus.CREATED.name())
                .createdAt(Timestamp.from(Instant.now()))
                .build();

        return workOrderRepository.save(wo);
    }

    @Override
    public WorkOrderResponse getWorkOrder(ApiRequest req) {
        Optional<WorkOrder> workOrder = workOrderRepository.findById(req.id());

        return workOrder.map(wo -> new WorkOrderResponse(
                wo.getId(),
                wo.getWoNumber(),
                wo.getVehicleId().longValue(),
                wo.getMechanicId().longValue(),
                wo.getType(),
                wo.getStatus(),
                wo.getComplaint()
        )).orElseThrow(() -> new NotFoundException("data not found"));
    }

    @Override
    public List<WorkOrderResponse> getWorkOrdersByMechanic(ApiRequest req) {
        var mechanic = mechanicRepository.findById(req.id());
        if (mechanic.isEmpty()) {
            throw new NotFoundException("data not found");
        }

        return workOrderRepository.findAllByMechanicId(req.id());
    }

    @Override
    public List<WorkOrderListResponse> getWorkOrders(WorkOrderListRequest req) {
        int page = Math.max(req.pageReq().page(), 1);
        int limit = req.pageReq().size();

        int offset = (page - 1) * limit;
        return workOrderRepository.findAll(offset, limit, req.plateNumber());
    }

    @Override
    @Transactional
    public void updateStatus(long id, WorkOrderStatus nextStatus) {
        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("work order not found!"));

        if (!WorkOrderWorkflow.canTransition(WorkOrderType.valueOf(workOrder.getType()), WorkOrderStatus.valueOf(workOrder.getStatus()), nextStatus)) {
            throw new BusinessException(String.format("Cannot change status from %s to %s", workOrder.getStatus(), nextStatus));
        }

        workOrderRepository.updateStatus(id, nextStatus);
    }
}
