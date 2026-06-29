package com.asein.workshop.workorder;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.workorder.dto.*;
import jakarta.validation.Valid;

import java.util.List;

public interface WorkOrderService {
    WorkOrderResponse create(@Valid WorkOrderCreateRequest req);

    WorkOrderResponse getWorkOrder(@Valid ApiRequest req);

    List<WorkOrderResponse> getWorkOrdersByMechanic(@Valid ApiRequest req);

    List<WorkOrderListResponse> getWorkOrders(@Valid WorkOrderListRequest req);

    void updateStatus(long id, @Valid WorkOrderStatus req);
}
