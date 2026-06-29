package com.asein.workshop.mechanic;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.common.ApiResponse;
import com.asein.workshop.mechanic.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/mechanic")
public class MechanicController {
    private final MechanicService mechanicService;

    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<MechanicListResponse>>> get(@Valid @RequestBody MechanicListRequest req) {
        List<MechanicListResponse> mechanics = mechanicService.getMechanics(req);
        return ResponseEntity.ok(
                ApiResponse.success(
                "retrieve data successful",
                        mechanics
                )

        );
    }

    @GetMapping("get")
    public ResponseEntity<ApiResponse<MechanicResponse>> get(@Valid @RequestBody ApiRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "retrieve data successful",
                        mechanicService.getMechanic(req)
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<MechanicResponse>> create(@Valid @RequestBody MechanicCreateRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "data has created",
                        mechanicService.create(req)
                )
        );
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<MechanicResponse>> update(@Valid @RequestBody MechanicUpdateRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "data has updated",
                        mechanicService.update(req)
                )
        );
    }

    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<ApiResponse<Void>> updateStatus(@Valid @PathVariable("id") long id, @PathVariable("status") String status) {
        mechanicService.updateStatus(id, status);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "status has updated",
                        null
                )
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@Valid @RequestBody ApiRequest req) {
        mechanicService.delete(req);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "data has deleted",
                        null
                )
        );
    }
}
