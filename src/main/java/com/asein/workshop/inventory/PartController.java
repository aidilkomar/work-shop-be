package com.asein.workshop.inventory;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.common.ApiResponse;
import com.asein.workshop.inventory.dto.PartCreateRequest;
import com.asein.workshop.inventory.dto.PartListRequest;
import com.asein.workshop.inventory.dto.PartResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/part")
public class PartController {

    @PostMapping
    public ResponseEntity<ApiResponse<PartResponse>> create(@Valid @RequestBody PartCreateRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "data has created",
                        null
                )
        );
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<PartResponse>> get(@Valid @RequestBody ApiRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "retrieve data successful",
                        null
                )
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<PartResponse>>> getAll(@Valid @RequestBody PartListRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "retrieve data successful",
                        null
                )
        );
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<PartResponse>> update(@Valid @RequestBody ApiRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "data has updated",
                        null
                )
        );
    }
}
