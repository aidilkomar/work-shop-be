package com.asein.workshop.vehicle;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.vehicle.dto.VehicleCreateRequest;
import com.asein.workshop.vehicle.dto.VehicleListRequest;
import com.asein.workshop.vehicle.dto.VehicleResponse;
import com.asein.workshop.vehicle.dto.VehicleUpdateRequest;

import java.util.List;

public interface VehicleService {
    List<VehicleResponse> getCustomers(VehicleListRequest req);
    VehicleResponse getCustomer(ApiRequest req);
    VehicleResponse create(VehicleCreateRequest req);
    VehicleResponse update(VehicleUpdateRequest req);
    void delete(ApiRequest req);
}
