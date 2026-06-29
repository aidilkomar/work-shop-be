package com.asein.workshop.customer;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.common.ApiResponse;
import com.asein.workshop.customer.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<CustomerListResponse>>> get(@Valid @RequestBody CustomerListRequest req) {
        List<CustomerListResponse> customers =  customerService.getCustomers(req);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "retrieve data successful",
                        customers,
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<CustomerResponse>> get(@Valid @RequestBody ApiRequest req) {
        var customer = customerService.getCustomer(req);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "retrieve data successful",
                        customer,
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CustomerResponse>> create(@Valid @RequestBody CustomerCreateRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "customer has created",
                        customerService.create(req),
                        LocalDateTime.now()
                )
        );
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<CustomerResponse>> update(@Valid @RequestBody CustomerUpdateRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "customer has updated",
                        customerService.update(req),
                        LocalDateTime.now()
                )
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@Valid @RequestBody ApiRequest req) {
        customerService.delete(req);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "customer has deleted",
                        null,
                        LocalDateTime.now()
                )
        );
    }
}
