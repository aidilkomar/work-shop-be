package com.asein.workshop.vehicle;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.common.ApiResponse;
import com.asein.workshop.vehicle.dto.VehicleCreateRequest;
import com.asein.workshop.vehicle.dto.VehicleListRequest;
import com.asein.workshop.vehicle.dto.VehicleResponse;
import com.asein.workshop.vehicle.dto.VehicleUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> get(@Valid @RequestBody VehicleListRequest req) {
        List<VehicleResponse> vehicles = vehicleService.getCustomers(req);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "retrieve data successful",
                        vehicles,
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<VehicleResponse>> get(@Valid @RequestBody ApiRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                    "retrieve data successful",
                    vehicleService.getCustomer(req),
                    LocalDateTime.now()
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<VehicleResponse>> create(@Valid @RequestBody VehicleCreateRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "data has created",
                        vehicleService.create(req),
                        LocalDateTime.now()
                )
        );
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<VehicleResponse>> update(@Valid @RequestBody VehicleUpdateRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "data has updated",
                        vehicleService.update(req),
                        LocalDateTime.now()
                )
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@Valid @RequestBody ApiRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "data has deleted",
                        null,
                        LocalDateTime.now()
                )
        );
    }
}
