package com.asein.workshop.workorder;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.common.ApiResponse;
import com.asein.workshop.workorder.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-order")
public class WorkOrderController {
    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<WorkOrderResponse>> create(@Valid @RequestBody WorkOrderCreateRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "data has created",
                        workOrderService.create(req)
                )
        );
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<WorkOrderResponse>> get(@Valid @RequestBody ApiRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "retrieve data successful",
                        workOrderService.getWorkOrder(req)
                )
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<WorkOrderListResponse>>> getList(@Valid @RequestBody WorkOrderListRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "retrieve data successful",
                        workOrderService.getWorkOrders(req)
                )
        );
    }

    @GetMapping("/mechanic/{id}")
    public ResponseEntity<ApiResponse<List<WorkOrderResponse>>> getListByMechanic(@Valid @RequestBody ApiRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "retrieve data successful",
                        workOrderService.getWorkOrdersByMechanic(req)
                )
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateStatus(@PathVariable long id, @Valid @RequestBody WorkOrderUpdateStatusRequest req) {
        workOrderService.updateStatus(id, req.status());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Work order status updated",
                        null
                )
        );
    }
}
