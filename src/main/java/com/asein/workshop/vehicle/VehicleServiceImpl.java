package com.asein.workshop.vehicle;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.common.exception.ConflictException;
import com.asein.workshop.common.exception.NotFoundException;
import com.asein.workshop.customer.CustomerRepository;
import com.asein.workshop.vehicle.dto.VehicleCreateRequest;
import com.asein.workshop.vehicle.dto.VehicleListRequest;
import com.asein.workshop.vehicle.dto.VehicleResponse;
import com.asein.workshop.vehicle.dto.VehicleUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, CustomerRepository customerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<VehicleResponse> getCustomers(VehicleListRequest req) {
        int page = Math.max(req.pageReq().page(), 1);
        int size = req.pageReq().size();

        int offset = (page - 1) * size;

        return vehicleRepository.findAll(offset, size, req);
    }

    @Override
    public VehicleResponse getCustomer(ApiRequest req) {
        var vehicle = vehicleRepository.findById(req.id());
        if (vehicle.isEmpty()) throw new NotFoundException("data not found");
        var v = vehicle.get();

        return new VehicleResponse(
                v.getId(),
                v.getCustomerId(),
                v.getPlateNumber(),
                v.getBrand(),
                v.getModel(),
                v.getYear(),
                v.getEngineNumber()
        );
    }

    @Override
    public VehicleResponse create(VehicleCreateRequest req) {
        vehicleRepository.findByPlatNumber(req.plateNumber()).ifPresent(v -> {
            log.info("data vehicle with plate number {} already exists", v.getPlateNumber());
            throw new ConflictException("data already exist");
        });

        customerRepository.findById(req.customerId()).orElseThrow(() -> {
            log.info("data customer with id {} not found", req.customerId());
            return new NotFoundException("data not found");
        });

        var vehicle = Vehicle.builder()
                .plateNumber(req.plateNumber())
                .customerId(req.customerId())
                .brand(req.brand())
                .model(req.model())
                .year(req.year())
                .engineNumber(req.engineNumber())
                .createdAt(LocalDateTime.now())
                .build();

        return vehicleRepository.save(vehicle);
    }

    @Override
    public VehicleResponse update(VehicleUpdateRequest req) {
        var vehicle = vehicleRepository.findById(req.id());

        return vehicle.map(v -> {

            if (!v.getPlateNumber().equals(req.vehicle().plateNumber())) {
                vehicleRepository.findByPlatNumber(req.vehicle().plateNumber())
                        .ifPresent(vh -> {
                            log.info("vehicle with plate number {} already exist", req.vehicle().plateNumber());
                            throw new ConflictException("plate number already exist");
                        });
            }

            if (!v.getCustomerId().equals(req.vehicle().customerId())) {
                customerRepository.findById(req.vehicle().customerId()).orElseThrow(() -> {
                    log.info("data customer with id {} not found", req.vehicle().customerId());
                    return new NotFoundException("data customer not found");
                });
            }

            v.setCustomerId(req.vehicle().customerId());
            v.setPlateNumber(req.vehicle().plateNumber());
            v.setBrand(req.vehicle().brand());
            v.setModel(req.vehicle().model());
            v.setYear(req.vehicle().year());
            v.setEngineNumber(req.vehicle().engineNumber());

            return vehicleRepository.save(v);
        }).orElseThrow(() -> {
            log.info("data vehicle with id {} not found", req.id());
            return new NotFoundException("data not found");
        });
    }

    @Override
    public void delete(ApiRequest req) {
        var isDeleted = vehicleRepository.delete(req.id());
        if (!isDeleted) {
            log.info("data vehicle with id {} not found", req.id());
            throw new NotFoundException("data not found");
        }
    }
}
