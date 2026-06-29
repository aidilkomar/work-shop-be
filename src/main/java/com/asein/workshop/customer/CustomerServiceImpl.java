package com.asein.workshop.customer;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.common.exception.NotFoundException;
import com.asein.workshop.customer.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse create(CustomerCreateRequest req) {
        var cust = Customer.builder()
                .name(req.name())
                .phone(req.phone())
                .address(req.address())
                .createdAt(Timestamp.from(Instant.now()))
                .build();
        return customerRepository.save(cust);
    }

    @Override
    public List<CustomerListResponse> getCustomers(CustomerListRequest req) {
        int page = Math.max(req.pageReq().page(), 1);
        int size = Math.max(req.pageReq().size(), 1);

        int offset = (page - 1) * size;
        return customerRepository.findAll(offset, size, req.name());
    }

    @Override
    public CustomerResponse getCustomer(ApiRequest req) {
        var cust = customerRepository.findById(req.id());

        if (cust.isEmpty()) {
            throw new NotFoundException("customer not found!");
        }

        var c = cust.get();

        return new CustomerResponse(c.getName(), c.getPhone(), c.getAddress(),  c.getCreatedAt().toString());
    }

    @Override
    public CustomerResponse update(CustomerUpdateRequest req) {
        var cust = customerRepository.findById(req.id()).orElseThrow(() -> new NotFoundException("customer not found"));

        cust.setName(req.customer().name());
        cust.setPhone(req.customer().phone());
        cust.setAddress(req.customer().address());

        var c = customerRepository.update(cust);

        if (c.isEmpty()) throw new RuntimeException("internal server error");


        return new CustomerResponse(
                c.get().getName(),
                c.get().getPhone(),
                c.get().getAddress(),
                c.get().getCreatedAt().toString());
    }

    @Override
    public void delete(ApiRequest req) {
        var customer = customerRepository.delete(req.id());
        if (!customer) throw new NotFoundException("data not found");
    }
}
