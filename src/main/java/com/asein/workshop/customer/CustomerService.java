package com.asein.workshop.customer;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.customer.dto.*;
import jakarta.validation.Valid;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(@Valid CustomerCreateRequest req);

    List<CustomerListResponse> getCustomers(@Valid CustomerListRequest req);

    CustomerResponse getCustomer(@Valid ApiRequest req);

    CustomerResponse update(@Valid CustomerUpdateRequest req);

    void delete(@Valid ApiRequest req);
}
